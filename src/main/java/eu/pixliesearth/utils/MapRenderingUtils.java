package eu.pixliesearth.utils;

import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import lombok.Getter;
import lombok.Setter;

public class MapRenderingUtils {
	
	public static void HandleRendering(MapView mapView, String pathToResource) {
		mapView.getRenderers().forEach((r) -> mapView.removeRenderer(r));
		CustomMapRenderer customMapRenderer = new CustomMapRenderer();
		customMapRenderer.setImage(pathToResource);
		mapView.addRenderer(customMapRenderer);
	}
	
	public static class CustomMapRenderer extends MapRenderer {

		private SoftReference<BufferedImage> image;
		private boolean rendered = false;
		private @Getter @Setter boolean shouldRerender = false;
		
		public void setImage(BufferedImage image) {
			this.image = new SoftReference<BufferedImage>(image);
		}
		
		public void setImage(String pathToResourceImage) {
			this.image = new SoftReference<BufferedImage>(getImage(getUrlFromResourcePath(pathToResourceImage)));
		}

		@Override
		public void render(MapView view, MapCanvas canv, Player p) {
			if (!this.shouldRerender && this.rendered) return;
			this.rendered = true;
			if (this.image.get()==null) setUnknownImage();
			canv.drawImage(0, 0, this.image.get());
		}
		
		private void setUnknownImage() {
			this.image = new SoftReference<BufferedImage>(getImage(getUrlFromResourcePath("icons/ErrorImage.png")));
		}
		
		public BufferedImage getImage(URL url) {
			boolean useCache = ImageIO.getUseCache();
			BufferedImage image = null;
			try {
		        ImageIO.setUseCache(false);
		        image = ImageIO.read(url);
			} catch (Exception ignore) {
		    	
		    } finally {
		    	ImageIO.setUseCache(useCache);
			}
			return image;
		}
		
		private URL getUrlFromResourcePath(String path) {
			return getClass().getClassLoader().getResource(path);
		}
	}
	
}

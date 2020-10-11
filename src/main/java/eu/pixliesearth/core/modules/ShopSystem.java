package eu.pixliesearth.core.modules;

import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopSystem implements Module, CommandExecutor {

    @Override
    public String name() {
        return "ShopSystem";
    }

    @Override
    public boolean isEnabled() {
        return config.getBoolean("shopsystem.enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        //Player player = (Player) sender;
        switch (args.length) {
            case 0:
/*                Gui gui = new Gui(instance, 6, "&bShop");
                PaginatedPane items = new PaginatedPane(0, 1, 9, 4);
                StaticPane categories = new StaticPane(0, 0, 9, 1);
                StaticPane panel = new StaticPane(0, 4, 9, 1);
                // NEXT PAGE
                if (items.getPages() + 1 <= items.getPages())
                    panel.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Black Arrow Right\\\"},SkullOwner:{Id:\\\"79f13daf-4884-40ab-8e35-95e472463321\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19").setDisplayname("&aNext page").build(), event -> {
                        items.setPage(items.getPage() + 1);
                    }), 8, 0);

                if (items.getPages() - 1 >= 0)
                    panel.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Black Arrow Left\\\"},SkullOwner:{Id:\\\"5fecc571-bcbb-4aaa-b53c-b5d8715dbe37\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=").setDisplayname("&aPrevious page").build(), event -> {
                        items.setPage(items.getPage() - 1);
                    }), 0, 0);

                gui.show(player);*/
                break;
        }
        return false;
    }

}

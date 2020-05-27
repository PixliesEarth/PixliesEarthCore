package eu.pixliesearth.core.commands;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.utils.ItemBuilder;
import eu.pixliesearth.core.utils.SkullBuilder;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());

        Gui gui = new Gui(Main.getInstance(), 3, "§bChoose your language");
        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Germany\\\"},SkullOwner:{Id:\\\"be211c23-d8aa-4119-bd0d-7f50fd115d9f\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").setDisplayname("§eGerman").build(), e -> {
            profile.setLang("DE");
            profile.save();
            player.closeInventory();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 0, 0);
        pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"United States of America\\\"},SkullOwner:{Id:\\\"3c30484a-76d3-4cfe-88e5-e7599bc9ac4d\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").setDisplayname("§eEnglish").build(), e -> {
            profile.setLang("ENG");
            profile.save();
            player.closeInventory();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 1, 0);
        gui.addPane(pane);
        gui.show(player);

        return false;
    }

}

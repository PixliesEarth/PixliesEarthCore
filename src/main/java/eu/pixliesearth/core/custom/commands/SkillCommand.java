package eu.pixliesearth.core.custom.commands;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand.TabableSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.skills.GetSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.LeaderboardSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.RanksSkill;
import eu.pixliesearth.core.custom.commands.subcommands.skills.ViewSkills;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SkillCommand extends CustomCommand {

	@Override
	public String getCommandName() {
		return "skill";
	}

	@Override
	public String getCommandDescription() {
		return "A command to interface with skills";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		ChestGui gui = new ChestGui(5, "§eSkills");
		Player player = (Player) commandSender;

		StaticPane pane = new StaticPane(0, 0, 9, 5);
		pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
		pane.addItem(new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getUniqueId()).setDisplayName("§6" + player.getName()).build(), e -> e.setCancelled(true)), 4, 1);
		int i = 0;
		String[] slots = {
			"2;1", "2;2", "2;3", "2;4", "2;5", "2;6", "2;7",
				"3;3", "3;4", "3;5"
		};
		for (Skill skill : SkillHandler.getSkillHandler().getSkills()) {
			int currentLevel = SkillHandler.getSkillHandler().getLevelOf(player.getUniqueId(), skill.getSkillUUID());
			int currentXP = SkillHandler.getSkillHandler().getXPOf(player.getUniqueId(), skill.getSkillUUID());
			int nextLevel = Math.min(currentLevel + 1, skill.getMaxSkillLevel());
			String lore1 = currentLevel == nextLevel ? "§aYou have mastered this skill." : "§3§l" + currentLevel + " §8[" + Methods.getProgressBar(currentXP, skill.getExperienceFromLevel(currentLevel), 10, "|", "§3", "§7") + "§8] §3§l" + nextLevel;
			pane.addItem(new GuiItem(new ItemBuilder(skillIcons.get(skill.getSkillUUID())).setDisplayName("§e§l" + skill.getSkillName()).addLoreLine("§7" + skill.getDescription()).addLoreLine(" ").addLoreLine(lore1).addLoreLine(" ").addLoreLine("§7XP: §3" + currentXP + "§7/§3" + skill.getExperienceFromLevel(currentLevel)).build(), event -> {
				event.setCancelled(true);
				renderSkillMenu(player, skill);
			}), Integer.parseInt(slots[i].split(";")[1]), Integer.parseInt(slots[i].split(";")[0]));
			i++;
		}
		gui.addPane(pane);
		gui.show(player);
		return true;
	}

	private static void renderSkillMenu(Player player, Skill skill) {
		ChestGui gui = new ChestGui(6, "§3" + skill.getSkillName());

		StaticPane pane = new StaticPane(0, 0, 9, 6);

		pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));

		GuiItem yellowPane = new GuiItem(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
		GuiItem whitePane = new GuiItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
		GuiItem orangePane = new GuiItem(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));

		OfflinePlayer firstPlace = Bukkit.getOfflinePlayer(SkillHandler.getSkillHandler().getLeaderboardOf(skill.getSkillUUID()).get(0));
		OfflinePlayer secondPlace = Bukkit.getOfflinePlayer(SkillHandler.getSkillHandler().getLeaderboardOf(skill.getSkillUUID()).get(1));
		OfflinePlayer thirdPlace = Bukkit.getOfflinePlayer(SkillHandler.getSkillHandler().getLeaderboardOf(skill.getSkillUUID()).get(2));

		GuiItem firstPlaceItem = new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(firstPlace.getUniqueId()).setDisplayName("§6§l1§8. §7" + firstPlace.getName()).addLoreLine("§7XP: §b" + SkillHandler.getSkillHandler().getXPOf(firstPlace.getUniqueId(), skill.getSkillUUID())).build(), event -> event.setCancelled(true));
		GuiItem secondPlaceItem = new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(secondPlace.getUniqueId()).setDisplayName("§f§l2§8. §7" + secondPlace.getName()).addLoreLine("§7XP: §b" + SkillHandler.getSkillHandler().getXPOf(secondPlace.getUniqueId(), skill.getSkillUUID())).build(), event -> event.setCancelled(true));
		GuiItem thirdPlaceItem = new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(thirdPlace.getUniqueId()).setDisplayName("§c§l3§8. §7" + thirdPlace.getName()).addLoreLine("§7XP: §b" + SkillHandler.getSkillHandler().getXPOf(thirdPlace.getUniqueId(), skill.getSkillUUID())).build(), event -> event.setCancelled(true));

		int currentLevel = SkillHandler.getSkillHandler().getLevelOf(player.getUniqueId(), skill.getSkillUUID());
		int currentXP = SkillHandler.getSkillHandler().getXPOf(player.getUniqueId(), skill.getSkillUUID());
		int nextLevel = Math.min(currentLevel + 1, skill.getMaxSkillLevel());
		String lore1 = currentLevel == nextLevel ? "§aYou have mastered this skill." : "§3§l" + currentLevel + " §8[" + Methods.getProgressBar(currentXP, skill.getExperienceFromLevel(currentLevel), 10, "|", "§3", "§7") + "§8] §3§l" + nextLevel;
		GuiItem skillIcon = new GuiItem(new ItemBuilder(skillIcons.get(skill.getSkillUUID())).setDisplayName("§e§l" + skill.getSkillName()).addLoreLine("§7" + skill.getDescription()).addLoreLine(" ").addLoreLine(lore1).addLoreLine(" ").addLoreLine("§7XP: §3" + currentXP + "§7/§3" + skill.getExperienceFromLevel(currentLevel)).build(), event -> event.setCancelled(true));

		pane.addItem(firstPlaceItem, 4, 1);
		pane.addItem(yellowPane, 4, 2);
		pane.addItem(yellowPane, 4, 3);
		pane.addItem(yellowPane, 4, 4);

		pane.addItem(secondPlaceItem, 3, 2);
		pane.addItem(whitePane, 3, 3);
		pane.addItem(whitePane, 3, 4);

		pane.addItem(thirdPlaceItem, 5, 3);
		pane.addItem(orangePane, 5, 4);

		pane.addItem(skillIcon, 1, 3);

		gui.addPane(pane);

		gui.show(player);
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableSubCommand(new GetSkill(), new ViewSkills(), new LeaderboardSkill(), new RanksSkill())};
	}

	private static final Map<String, Material> skillIcons = new HashMap<>();

	static {
		skillIcons.put("Pixlies:Lumbering", Material.IRON_AXE);
		skillIcons.put("Pixlies:Mining", Material.DIAMOND_PICKAXE);
		skillIcons.put("Pixlies:Building", Material.SCAFFOLDING);
		skillIcons.put("Pixlies:Traveling", Material.LEATHER_BOOTS);
		skillIcons.put("Pixlies:Farming", Material.IRON_HOE);
		skillIcons.put("Pixlies:Hunting", Material.BOW);
		skillIcons.put("Pixlies:Enchanting", Material.ENCHANTED_BOOK);
		skillIcons.put("Pixlies:Fishing", Material.FISHING_ROD);
		skillIcons.put("Pixlies:Taming", Material.WHEAT);
		skillIcons.put("Pixlies:Questing", Material.COMPASS);
	}

}

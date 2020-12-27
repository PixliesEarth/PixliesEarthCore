package eu.pixliesearth.pixliefun;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class PixlieFunGuide {

    public static void giveBooks(Player player) {
        player.getInventory().addItem(getServerGuide());
        player.getInventory().addItem(getPixliefunGuide());
    }

    public static ItemStack getPixliefunGuide() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        // FIRST PAGE
        bookMeta.addPage(
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        Welcome to               PixliesEarth!"
        );

        // SECOND PAGE
        bookMeta.addPage(
                "PixlieFun is a customcoded plugin that is like a mod-pack. " +
                        "You can have electricity, ICBMs, nuclear reactors and various armor and foods."
        );

        // THIRD PAGE
        bookMeta.addPage(
                "First of, you can see all the recipes for PixlieFun in §b/recipes§r. " +
                        "The first thing you should craft is a custom-crafting table. " +
                        "You can craft it with the following recipe:\n" +
                "R | S | S\n" +
                "S | C | S\n" +
                "R | S | R\n" +
                "R = Redstone\n" +
                "S = Stick\n" +
                "C = Crafting table"
        );

        // FOURTH PAGE
        bookMeta.addPage(
                "The custom crafting table is where you craft most fo the items. " +
                        "In PixlieFun you need a lot of dusts, you can get dusts by crushing ores. " +
                        "To crush ores, you need an ore crusher. " +
                        "You can find it's recipe in §b/recipes§r. " +
                        "But the ore crusher needs energy, that's no problem!"
        );

        // FIFTH PAGE
        bookMeta.addPage(
                "Before you can get an automatic energy system, you can get a manual generator. " +
                        "The manual generator takes your mana and turns it into electricity to power machines next to the generator. " +
                        "To generate energy, you need to right/left-click the manual generator."
        );

        // SIXTH PAGE
        bookMeta.addPage(
                "A good machine to craft is the forge. " +
                        "The forge also has an electric alternative, but that's not important for us rightnow. " +
                        "You can use the forge to forge ingots using dusts. " +
                        "The forge uses 1x Carbon Dust per usage, which you have to put in the buttom-right slot."
        );

        // SEVENTH PAGE
        bookMeta.addPage(
                "There are over 300+ more items and machines for you to craft. " +
                        "You can view all of them using §b/recipes§r. " +
                        "Thank you for reading this starter-guide, you can get more information on our wiki or by asking our staff members, which are always there for you to help!"
        );

        // META
        bookMeta.setTitle("§cPixlieFun guide");
        bookMeta.setAuthor("PixliesNet");

        // SET ITEM META
        book.setItemMeta(bookMeta);

        // CUSTOM MODEL DATA
        book.setCustomModelData(4);

        return book;
    }

    public static ItemStack getServerGuide() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        // FIRST PAGE
        bookMeta.addPage(
                "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "        Welcome to               PixliesEarth!"
        );

        // SECOND PAGE
        bookMeta.addPage(
                "Hey you! ...yes I mean you, don't look around ;D Welcome to Pixlies! We are more than happy to " +
                        "have you here. As you may know, we are an earth-server. Wait, you don't know what an earth server is?" +
                        "No problem my friend! Just scroll through this guide book and you will love it!"
        );

        // THIRD PAGE
        bookMeta.addPage(
                "Basically it is a simulation of geopolitics in Minecraft. " +
                        "You can create your own nation, join existing ones, go on conquests and build glorious empires. " +
                        "To create a nation, you need to do §b/n create NAME §rGreat! " +
                        "You just did the first step in creating a nation."
        );

        // FOURTH PAGE
        bookMeta.addPage(
                "You can now claim territory around the map. " +
                        "You can see our map online on §ehttps://pixlies.net/map§r. " +
                        "You can use /warp to go to the nearest warp to your desired location. " +
                        "Claiming is pretty easy, if you do §b/n claim auto§r, you can walk and claim."
        );

        // FIFTH PAGE
        bookMeta.addPage(
                "You can also claim single chunks using §b/n claim one§r. " +
                        "If you claimed a hollow shape, you can fill it using §b/n claim fill§r. " +
                        "You'll see pretty fast that you don't have enough claiming power. " +
                        "That's no problem! " +
                        "You can invite your friends to your nation to get more claiming power."
        );

        // SIXTH PAGE
        bookMeta.addPage(
                "To invite people in your nation, you need to do §b/n invite PLAYER§r. " +
                        "They can then click on accept to join your nation, or do §b/n join NATION§r. " +
                        "If inviting people isn't enough, you can also upgrade your nation Era to get more power."
        );

        // SEVENTH PAGE
        bookMeta.addPage(
                "To upgrade your era, you need to get PoliticalPower. " +
                        "Nations get political power, if a player has more than 30 levels. " +
                        "Every level-up after level 30 gives the nation a random amount of Political Power between 0.2 and 1.0. " +
                        "If you get enough PP, you can upgrade your Era in §b/n menu§r."
        );

        // EIGHT PAGE
        bookMeta.addPage(
                "You can find more detailed information about Eras on your wiki.\n" +
                "To allow your members to teleport to your cities/settlements, you can create nation settlements using §b/n settlements add NAME§r. " +
                        "You can teleport to them using §b/n settlements§r."
        );

        // NINTH PAGE
        bookMeta.addPage(
                "You will see, that everytime you teleport, that number on your scoreboard becomes smaller. " +
                        "That's your mana, it is used for teleportations and you get an amount of mana, depending on your Era, every 5 minutes. " +
                        "If you don't have mana, you can't teleport."
        );

        // TENTH PAGE
        bookMeta.addPage(
                "Thank you for reading this guide, if you have any more questions, you can always ask our staff team. " +
                        "We will be more than happy to help you.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "§c❤ §rYour PixliesNet team"
        );

        // META
        bookMeta.setTitle("§bServer guide");
        bookMeta.setAuthor("PixliesNet");

        // SET ITEM META
        book.setItemMeta(bookMeta);

        // CUSTOM MODEL DATA
        book.setCustomModelData(3);

        return book;
    }

}

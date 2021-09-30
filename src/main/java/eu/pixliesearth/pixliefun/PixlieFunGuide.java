package eu.pixliesearth.pixliefun;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class PixlieFunGuide {

    public static void giveBooks(Player player) {
        player.getInventory().addItem(getServerGuide());
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

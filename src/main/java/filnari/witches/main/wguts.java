package filnari.witches.main;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class wguts extends JavaPlugin implements Listener {
	
	public List<String> books;
	public int chance;
	
	@Override
	// This is run when the server starts
	public void onEnable() {
		getLogger().info("Witches started");
		getServer().getPluginManager().registerEvents(this, this);

		// moving books to config.yml file
		FileConfiguration config;
		if (!new File(getDataFolder(),"config.yml").exists())
		{
			saveResource("config.yml",false);
			getLogger().info("config.yml not found - creating a new config.");
			// write fresh config here
			saveDefaultConfig();
		}else{
			config = getConfig();
			getLogger().info("config.yml found - loading");
			// load config here
		}
		// Read the books from config.yml
		books = getConfig().getStringList("books");
		getLogger().info("Loaded "+books.size()+" books");
		
		// get the drop chance for books from config.yml
		chance = getConfig().getInt("dropchance");
		getLogger().info("Drop chance read: " + chance +"%");
	}
	@Override
	public void onDisable() {
		getLogger().info("Witches stopped");
		// This is run when the server stops
	}


@EventHandler
	public void dropbook(EntityDeathEvent event)
	{
	LivingEntity entity = event.getEntity();
	Player player = entity.getKiller();
	
	Random rn = new Random();

	double xx = (double) chance / (double) 100;
	double yy = Math.random();
	getLogger().info("testing " + yy + " < " + xx);
	if (entity instanceof Witch && player != null && yy < xx ) {
		// player.sendMessage("You killed a witch");
		
		// Make the meta data object
		ItemFactory factory = Bukkit.getItemFactory();
		BookMeta meta = (BookMeta) factory.getItemMeta(Material.WRITTEN_BOOK);
		meta.setAuthor("Witch Hazel");
		meta.setTitle("Gate Secrets");
		
		// build the first page
		StringBuilder page1 = new StringBuilder();
		String p1a = "The secret to quick travel around the world is �9/gate�0.\n\n";
		String p1b = "But, I'd be careful, old gates may be buried or hidden...\n\n";
		String p1c = "Some say that there are many gates around the world, but one we do know about is...";
		page1.append(p1a).append(p1b).append(p1c);

		// Choose a random second page
		StringBuilder page2 = new StringBuilder();
		int random = (int) (Math.random() * books.size());
		getLogger().info("Dropping book #" + random);
		page2.append(books.get(random));
		
		// convert page text to meta data
		meta.setPages(page1.toString(),page2.toString());
		
		// create the book item
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		
		// apply the page meta data to the book
		item.setItemMeta(meta);
		
		// To give it to the player: player.getInventory().addItem(item);
		// we just drop it on the ground
		World world = event.getEntity().getWorld();
		world.dropItem(entity.getLocation(),item);

				
		}
	}
	

}
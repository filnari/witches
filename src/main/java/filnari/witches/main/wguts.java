package filnari.witches.main;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
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
	@Override
	public void onEnable() {
		getLogger().info("Witches started");
		getServer().getPluginManager().registerEvents(this, this);
		// This is run when the server starts
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
	if (entity instanceof Witch && player != null) {
		player.sendMessage("You killed a witch");
		
		// Make the meta data object
		ItemFactory factory = Bukkit.getItemFactory();
		BookMeta meta = (BookMeta) factory.getItemMeta(Material.WRITTEN_BOOK);
		meta.setAuthor("Glinda");
		meta.setTitle("Gates Made Easy");
		
		// build the pages
		StringBuilder page1 = new StringBuilder();
		String p1a = "The secret to quick travel around the world is §9/gate§0.\n\n";
		String p1b = "But, I'd be careful, you never know where a gate may be buried or hidden...\n\n";
		String p1c = "Some say that there are many gates around the world, but one we do know about is...";
		page1.append(p1a).append(p1b).append(p1c);
		StringBuilder page2 = new StringBuilder();
		// String p2a = "Page 2, test 1\n";
		// String p2b = "Page 2, test 2\n";
		// page2.append(p2a).append(p2b);
		
		int random = (int) (Math.random() * 10 + 1);
		// getLogger().info("random is " + random);
		
		switch (random) {
		// color code §1 §0
			case 1:
				page2.append("Far to the southeast, there lies a wild unexplored peninsula, known to some as §aMonton§0.");
				break;
			case 2:
				page2.append("To the northwest is a lake, this gate hides below it's pristine surface. It is §aUmel§0.");
				break;
			case 3:
				page2.append("Hidden deep in a snowy forest in the north is §aNiveus§0.");
				break;
			case 4:
				page2.append("§aHoogte§0 is where the tree canopy is so thick not even grass will grow on the ground.");
				break;
			case 5:
				page2.append("Wedged in a tight canyon not far from town, you will find §aRoko§0.");
				break;
			case 6:
				page2.append("On the coast of the sunny plains to the southeast, you will find §aFungo§0.");
				break;
			case 7:
				page2.append("Recently found in a deep underground chasm, is §aAbismo§0.");
				break;
			case 8:
				page2.append("§aSelva§0 is a well known gate on the edge of a deep, dark jungle.");
				break;
			case 9:
				page2.append("In the plains of a wide savannah you can find §aUrbo§0.");
				break;
			case 10:
				page2.append("Found on a forested isle in the far northeast, you will find §aLafo§0.");
				break;
			default:
		}
		
		// convert page text to meta data
		// meta.setPages(page1.toString());
		meta.setPages(page1.toString(),page2.toString());
		
		// create the book item
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		// apply the page meta data to the book
		item.setItemMeta(meta);
		// and give it to the player
		// player.getInventory().addItem(item);
		// or drop it
		World world = event.getEntity().getWorld();
		world.dropItem(entity.getLocation(),item);
		getLogger().info("Dropped a book #" + random);
				
		}
	}
	

}
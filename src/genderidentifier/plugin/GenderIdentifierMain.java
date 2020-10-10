package genderidentifier.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GenderIdentifierMain extends JavaPlugin {
	
	Player player;
	String githubLink = "https://github.com/RidayB/GenderIdentifierMCPlugin";
	Map<String, Object> genderMap = new HashMap<>();
	Map<String, Object> pronounMap = new HashMap<>();
	
	
	
	
	public void onEnable() {
		GenderIdentifierListener listener = new GenderIdentifierListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
		
		genderMap = getConfig().getConfigurationSection("playerGenders").getValues(false);
		pronounMap = getConfig().getConfigurationSection("playerPronouns").getValues(false);
		
	}
	
	public void onDisable() {
		
		getConfig().createSection("playerGenders", genderMap);
		saveConfig();
		getConfig().createSection("playerPronouns", pronounMap);
		saveConfig();
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
			
			
			try {player = (Player) sender;
			}
			catch (Exception e) {
				sender.sendMessage(ChatColor.DARK_RED + "This command can only be run by ingame players!");
				return true;
			}
			
			if (sender instanceof Player) {
					String lowerCmd = cmd.getName().toLowerCase();
			
				switch (lowerCmd) {
				
				case "gender":
					
					if (args.length == 0) {
						
						if (player.hasPermission("genderidentifier.checkgender")) {
							if (genderMap.containsKey(player.getName())) {
								player.sendMessage(ChatColor.AQUA + "Your gender is: " + genderMap.get(player.getName()));
								return true;
							}
							
							else {
								player.sendMessage(ChatColor.DARK_RED + "You have not set your gender yet. Use /gender set (gender here).");
								return true;
							}
						}
						
						else {
							player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
							return true;
						}
						
						
					}
					
					else if (args.length == 1) {
						switch (args[0]) {
							case "help":
								if (player.hasPermission("genderidentifier.help")) {
									player.sendMessage(ChatColor.AQUA + "Read the README file here: ");
									player.sendMessage(ChatColor.AQUA + githubLink);
									return true;
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
								
							case "remove":
								if (player.hasPermission("genderidentifier.removegender")) {
									if (genderMap.containsKey(player.getName())) {
										genderMap.remove(player.getName());
										player.sendMessage(ChatColor.AQUA + "Successfully removed your gender.");
										return true;
									}
									
									else {
										player.sendMessage(ChatColor.AQUA + "You have not set your gender, nothing to remove.");
										return true;
									}
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
							
							case "list":
								
								if (player.hasPermission("genderidentifier.listallgenders")) {
									Set<String> names = genderMap.keySet();
									Collection<Object> genders = genderMap.values();
									Iterator<String> namesIt = names.iterator();
									Iterator<Object> gendersIt = genders.iterator();
									
									if (names.toArray().length == 0) {
										player.sendMessage(ChatColor.DARK_RED + "Nothing here yet.");
										return true;
									}
									
									while (namesIt.hasNext()) {
										while (gendersIt.hasNext()) {
											player.sendMessage(ChatColor.AQUA + "" + namesIt.next() + " : " + gendersIt.next());
										}
										
									}
									return true;
									
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
								
							
							 default:
								 
								if (player.hasPermission("genderidentifier.checksomeonegender")) {
									String playerTarget = (args[0]);
									if (genderMap.containsKey(playerTarget)) {
										player.sendMessage(ChatColor.AQUA + "" + playerTarget + "'s gender is " + genderMap.get(playerTarget) + "."); 
										return true;
									}	
									else {
										player.sendMessage(ChatColor.DARK_RED + "The person you selected is either not online, not a user, or has not set their pronoun.");
										return true;
									
									}
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
							
							
						
						}
					}
					
					else if (args.length == 2) {
						
						if (player.hasPermission("genderidentifier.setgender")) {
							switch (args[0]) {
							
							case "set":
								String playerGender = (args[1]);
								genderMap.put(player.getName(), playerGender);
								player.sendMessage(ChatColor.AQUA + "Successfully put " + playerGender + " as your gender.");
								return true;
								
						}
						}
						
						else {
							player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
							return true;
						}
					
						
					}
					
					
					else {
						player.sendMessage(ChatColor.DARK_RED + "This command takes in 0, 1, or 2 arguments.");
						return true;
					}
					
					
					
				case "pronoun":
				
					if (args.length == 0) {
						
						if (player.hasPermission("genderidentifier.checkpronoun")) {
							if (pronounMap.containsKey(player.getName())) {
								player.sendMessage(ChatColor.AQUA + "Your pronoun is: " + pronounMap.get(player.getName()));
								return true;
							}
							
							else {
								player.sendMessage(ChatColor.DARK_RED + "You have not set your pronoun yet. Use /pronoun set (pronoun here).");
								return true;
							}
						}
						
						else {
							player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
							return true;
						}
						}
						
					
					else if (args.length == 1) {
						switch (args[0]) {
							case "help":
								if (player.hasPermission("genderidentifier.help")) {
									player.sendMessage(ChatColor.AQUA + "Read the README file here: ");
									player.sendMessage(ChatColor.AQUA + githubLink);
									return true;
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
								
								
							case "remove":
								if (player.hasPermission("genderidentifier.removepronoun")) {
									if (pronounMap.containsKey(player.getName())) {
										pronounMap.remove(player.getName());
										player.sendMessage(ChatColor.AQUA + "Successfully removed your pronoun.");
										return true;
									}
									
									else {
										player.sendMessage(ChatColor.AQUA + "You have not set your pronoun, nothing to remove.");
										return true;
									}
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
								
							case "list":
								
								if (player.hasPermission("genderidentifier.listallpronouns")) {
									Set<String> names = pronounMap.keySet();
									Collection<Object> pronouns = pronounMap.values();
									Iterator<String> namesIt = names.iterator();
									Iterator<Object> pronounsIt = pronouns.iterator();
									
									if (names.toArray().length == 0) { 
										player.sendMessage(ChatColor.DARK_RED + "Nothing here yet.");
										return true;
									}
									
									while (namesIt.hasNext()) {
										while (pronounsIt.hasNext()) {
											player.sendMessage(ChatColor.AQUA + "" + namesIt.next() + " : " + pronounsIt.next());
										}
										
									}
									
									return true;
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
								
							 default:
								if (player.hasPermission("genderidentifier.checksomeonepronoun")) {
									String playerTarget = (args[0]);
									if (pronounMap.containsKey(playerTarget)) {
										player.sendMessage(ChatColor.AQUA + "" + playerTarget + "'s pronouns are " + pronounMap.get(playerTarget) + ".");
									}
									else {
										player.sendMessage(ChatColor.DARK_RED + "The person you selected is either not online, not a user, or has not set their pronoun.");
										return true;
									}
									return true;
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
							
						
						}
					}
					
					else if (args.length == 2) {
						switch (args[0]) {
							case "set":
								
								if (player.hasPermission("genderidentifier.setpronoun")) {
									String playerPronoun = (args[1]);
									pronounMap.put(player.getName(), playerPronoun);
									player.sendMessage(ChatColor.AQUA + "Successfully put " + playerPronoun + " as your pronoun.");
									return true;
									
								}
								
								else {
									player.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command.");
									return true;
								}
						}
					}
					
					else {
						player.sendMessage(ChatColor.DARK_RED + "This command takes in 0, 1, or 2 arguments.");
						return true;
					}
					

				}
			} 
			return true;
		}
	}

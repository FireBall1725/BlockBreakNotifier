#About BlockBreakNotifier
Have a PVP server and want to add a little fun and excitement or want to see if someone is X-Raying for diamonds?  Then this plugin might be for you.

This plugin watches for blocks that you configure in the config file to be broken, then sends out a message to those with the permission node letting them know that the block was broken.

##Features
Features include

* Radius check to know how many of the same block are around a user
* Log file
* Display Message can be customized
* Mute feature, mute the notifications
* And More!

##Commands

###/bbn
Base command, shows plugin version, and if you have mute turned on.

###/bbn help
Main help command, shows all the commands

###/bbn mute
Turn mute on / off

###/bbn reload
Reload the configuration files

##Permission Nodes
### blockbreaknotifier.getnotifications
If you have this permission node then you will be able to use the plugin's commands as well as see the notifications

### blockbreaknotifier.reload
Allows you to reload the configuration

##Configuration Files
**config.yml**
```yaml
# Block Notification Configuration File

Database:						# Database Configuration
  UseDatabase: false
  DatabaseServer: localhost
  DatabaseUsername: ''
  DatabasePassword: ''
  DatabaseName: minecraft
Blocks:							# Blocks you want to watch
- '56'
- '14'
- '15'
Block:
  CheckRadius: 5				# Radius
Notification:
  Cooldown: 15000				# Cool down time on messages in ms
  Message: ''					# Message Shown to users (supports standard minecraft &color codes)
```

##Message Options
* %playername% - Player Display Name
* %blockname% - Block Name (i.e. DIAMOND BLOCK)
* %blockid% - Block ID Number
* %worldname% - World Name
* %playerlocation% - Players Location (X, Y, Z)
* %blocklocation% - Blocks Location (X, Y, Z)
* %radiuscount% - Number of blocks around the block of the same type
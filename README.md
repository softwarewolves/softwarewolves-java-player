# Softwarewolves-java-player


This is an example implementation of a bot in java for the digital version of the werewolves party game (rebranded to softwarewolves game). The bot does not do much - it implements the lazy villager story.

More information on the softwarewolves game can be found at : [Softwarewolves documentation][1].

## Setting up the project


### 1. Get the code 


With github, there are several possibilities:
* Download the project as a zipfile from github (github button somewhere on page). 
* Fork the project to your own github repository (github button somewhere on page), then clone it. This requires a github account.
* Clone the repository to your own computer. This requires git to be installed on your system. For cloning, you can use your favorite git tool or the following command:

```
        git clone https://github.com/supernelis/softwarewolves-java-player.git 
```

### 2. Setup your editor

Get the project in your favorite editor:
* for Eclipse addicts: 
 * New java project
 * Uncheck the checkbox "Use default location" and point to where you checked out the java code


### 3. Setup the build path


Add the smack jar files to your buildpath (all 4 of them).

### 4. Configure the bot

Make sure you have a user on the jabber server for your bot and that you know the username of the game coordinator. See [Softwarewolves documentation][1] for more information on how to do that.

Change the configuration in the static variables of softwaresolves.javabot.Bot to point to the correct users and server, e.g. 


```
 public static final String XMPP_SERVER = "jabber.org";
	public static final String BOTUSERNAME = "javabot";
	public static final String BOTPWD = "ditiseengoedpaswoord";
	public static final String GAMECOORDINATOR = "sww";
```

### 5. Run

Run the main method in the class softwaresolves.javabot.Bot. A seperate window should pop-up with the smack debug output.


## Smack

This project uses the smack libraries for xmpp. Although it is not considered as good practice, 
we included the documentation and libraries of of smack in this git project to simplify setup, limit the 
amount of external dependencies that can fail and simplify offline working.

- Documentation: <a href="smack_3_2_2/documentation/">Offline</a>, [jive documentation website][2].
- API: <a href="smack_3_2_2/javadoc/">Offline</a>, [jive javadoc website][3].

[1]: https://github.com/supernelis/softwarewolves-doc
[2]: http://www.igniterealtime.org/builds/smack/docs/latest/documentation/
[3]: http://www.igniterealtime.org/builds/smack/docs/latest/javadoc/

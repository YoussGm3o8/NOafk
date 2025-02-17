# NOafk Plugin

A Nukkit plugin that automatically kicks players who are AFK (Away From Keyboard) for too long.

## Features

- Configurable AFK detection and kick timer
- Warning system before kicking
- Countdown display in last 10 seconds
- AFK immunity system for specific players
- Persistent ignored players list
- Clean rejoining (no instant kicks)

## Commands

- `/afkignore <player>` - Toggle AFK immunity for a player
  - Permission: `noafk.command.ignore`
  - Usage: Toggles whether a player can be kicked for being AFK

## Configuration

```yaml
# Time settings are in seconds
kick-after-seconds: 300         # Total AFK time before kick (default 5 minutes)
warning-before-seconds: 30      # Number of seconds before kick to issue a warning
warning-message: "You will be kicked for being AFK soon!"
ignored-players: []             # List of players immune to AFK kicks
```

## Permissions

- `noafk.command.ignore` - Allows use of the /afkignore command

## Installation

1. Download the plugin JAR file
2. Place it in your server's `plugins` folder
3. Restart the server
4. Configure the plugin in `plugins/NOafk/config.yml`

## Building from Source

1. Clone the repository
2. Build using Maven:
```bash
mvn clean package
```

## License

MIT License

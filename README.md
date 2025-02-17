# NOafk Nukkit Plugin

NOafk is a simple Nukkit plugin that automatically kicks players who have been inactive (AFK) for a configurable amount of time. It also optionally warns them before they get kicked, allowing them to move or chat to reset the timer.

## Features
- Configurable timeout duration via config.yml
- Optional warning message sent to players before the kick
- Minimal performance overhead

## Installation
1. Place the NOafk plugin JAR file into your server’s "plugins" folder.
2. Start or restart your Nukkit server.
3. A config.yml file will be generated in the "plugins/NOafk" folder after the first run.

## Configuration
Edit the values in "config.yml":
- kick-after-seconds: The total number of seconds of inactivity before a player is kicked.
- warning-before-seconds: The number of seconds before kick to send a warning message.
- warning-message: Text for the warning message.

## Usage
Once installed and the server is running, the plugin will monitor player activity. If a player exceeds the configured AFK threshold, they will receive a warning (if configured). If they remain inactive, they will be kicked.

## Permissions
No special permissions are required. All players are subject to the AFK check.

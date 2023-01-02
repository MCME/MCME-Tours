# MCME-Tours

Tour management plugin.<br/>
<i>Allows a Tour-Badge holder or a guide to create a temporary group that other players can join.<br/>
The hosts then have access to teleportation commands that can only be used on members of that group.<br/></i>
This is a bungee plugin, so it allows cross-server tours.
<br/>

Author: Jubo

## <b>Permissions</b>
All users with '<b>Tours.user</b>' permission node can:
- '<b>/tour help</b>' to find info on Tour-commands.
- '<b>/tour join \<tourname\></b>' to join a currently running tour.
- '<b>/tour leave</b>' to leave the currently running tour.
- '<b>/tour request</b>' sends an alert to any guides online and sends a message on discord.
- '<b>/ttp</b>' teleports the user to their tour guide.
- '<b>/tour chat</b>' switches the tour-chat on and off.
- '<b>/tour check</b>' checks if a tour is currently running.
- - '<b>/tc</b>' lets you write in tour-chat, when tour-chat is switched off.

Users with the '<b>Tours.ranger</b>' permission node can:
- '<b>/tour start</b>' creates new tour, names it after user, and announces it.
- '<b>/tour stop</b>' stops user's current tour and removes all group members.
- '<b>/tour list</b>' lists all users in the tour group.
- '<b>/tour hat</b>' allows the tour guide to wear their currently held block as a hat.
- '<b>/ttp \<player\></b>' teleports \<player\> to user. \<player\> must be part of tour group.
- '<b>/ttpa</b>' teleports all players in tour group to user.
- '<b>/tour kick \<player\></b>' kicks a player.
- '<b>/tour announce</b>' announces the tour ingame and in discord.
- '<b>/tour info \<message\></b>' sets a description for the tour announcement.
- '<b>/tour host \<player\></b>' sets a new host for the tour.
- '<b>/tour cohost \<player\></b>' sets co-hosts for the tour (less perms than host).
- '<b>/tour glow</b>' lets the host and their co-hosts glow and can switch it off.

## <b>License</b>

Tours is licensed under the GNU General Public License V3:<br/>
http://www.gnu.org/copyleft/gpl.html<br/>
<br/>
<i>In the spirit of good-faith that this source has been made available, the author requests accreditation for works, in the form of the follow tag, to be appended to any class that contains his/her original code:<br/>
(This request is an optional, additional courtesy)</i><br/>
/**<br/>
\* @author Jubo<br/>
\*/<br/>
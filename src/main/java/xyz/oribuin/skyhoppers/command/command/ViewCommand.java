package xyz.oribuin.skyhoppers.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.*;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import xyz.oribuin.skyhoppers.manager.HopperManager;
import xyz.oribuin.skyhoppers.manager.LocaleManager;

public class ViewCommand extends BaseRoseCommand {

    public ViewCommand(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @RoseExecutable
    public void execute(CommandContext context) {
        final var locale = this.rosePlugin.getManager(LocaleManager.class);
        final var manager = this.rosePlugin.getManager(HopperManager.class);

        final var player = (Player) context.getSender();
        final var block = player.getTargetBlock(5);
        final var hoppers = manager.getHopperViewers();

        // Disable the visualizer if the player is not looking at a skyhopper.
        if (block == null || (!(block.getState() instanceof Hopper)) || block.getType() == Material.AIR) {

            if (hoppers.containsKey(player.getUniqueId())) {
                hoppers.remove(player.getUniqueId());
                locale.sendMessage(player, "command-view-disabled");
                return;
            }

            locale.sendMessage(player, "command-view-not-skyhopper");
            return;
        }

        // Enable the visualizer if the player is looking at a skyhopper.
        var hopper = manager.getHopper(block);
        if (hopper == null) {
            locale.sendMessage(player, "command-view-not-skyhopper");
            return;
        }

        if (hoppers.containsKey(player.getUniqueId())) {
            hoppers.remove(player.getUniqueId());
            locale.sendMessage(player, "command-view-disabled");
            return;
        }

        hoppers.put(player.getUniqueId(), hopper);
        locale.sendMessage(player, "command-view-success");
    }

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder("view")
                .descriptionKey("command-view-description")
                .permission("skyhoppers.commands.view")
                .build();
    }
}

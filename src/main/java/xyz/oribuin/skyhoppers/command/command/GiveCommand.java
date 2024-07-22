package xyz.oribuin.skyhoppers.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.argument.ArgumentHandlers;
import dev.rosewood.rosegarden.command.framework.*;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import dev.rosewood.rosegarden.utils.StringPlaceholders;
import org.bukkit.entity.Player;
import xyz.oribuin.skyhoppers.hopper.SkyHopper;
import xyz.oribuin.skyhoppers.manager.HopperManager;
import xyz.oribuin.skyhoppers.manager.LocaleManager;

import java.util.logging.Level;

public class GiveCommand extends BaseRoseCommand {

    public GiveCommand(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @RoseExecutable
    public void execute(CommandContext context, Player player, Integer amount) {
        final var manager = this.rosePlugin.getManager(HopperManager.class);
        final var locale = this.rosePlugin.getManager(LocaleManager.class);

        this.rosePlugin.getServer().getLogger().log(Level.INFO, "Player: " + context.getSender().getName() + " | " + "Amount: " + amount);
        context.get(1);

        var newAmount = Math.max(1, Math.min(64, amount));

        final var placeholders = StringPlaceholders.builder("amount", newAmount)
                .add("player", player.getName())
                .build();

        var itemStack = manager.getHopperAsItem(new SkyHopper(), newAmount);
        player.getInventory().addItem(itemStack);
        locale.sendMessage(context.getSender(), "command-give-success", placeholders);
    }

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder("give")
                .descriptionKey("command-give-description")
                .permission("skyhoppers.commands.give")
                .arguments(ArgumentsDefinition.builder()
                        .required("player", ArgumentHandlers.PLAYER)
                        .required("amount", ArgumentHandlers.INTEGER)
                        .build())
                .build();
    }
}

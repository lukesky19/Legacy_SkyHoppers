package xyz.oribuin.skyhoppers.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.argument.ArgumentHandlers;
import dev.rosewood.rosegarden.command.framework.*;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import dev.rosewood.rosegarden.utils.StringPlaceholders;
import org.bukkit.Bukkit;
import xyz.oribuin.skyhoppers.hopper.SkyHopper;
import xyz.oribuin.skyhoppers.manager.HopperManager;
import xyz.oribuin.skyhoppers.manager.LocaleManager;

public class GiveAllCommand extends BaseRoseCommand {

    public GiveAllCommand(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @RoseExecutable
    public void execute(CommandContext context, Integer amount) {
        final var manager = this.rosePlugin.getManager(HopperManager.class);
        final var locale = this.rosePlugin.getManager(LocaleManager.class);

        int newAmount = Math.max(1, Math.min(64, amount));

        final StringPlaceholders placeholders = StringPlaceholders.builder("amount", newAmount)
                .add("players", Bukkit.getOnlinePlayers().size())
                .build();

        var itemStack = manager.getHopperAsItem(new SkyHopper(), newAmount);

        Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().addItem(itemStack));
        locale.sendMessage(context.getSender(), "command-giveall-success", placeholders);
    }

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder("giveall")
                .descriptionKey("command-giveall-description")
                .permission("skyhoppers.commands.giveall")
                .arguments(ArgumentsDefinition.builder()
                        .required("amount", ArgumentHandlers.INTEGER)
                        .build())
                .build();
    }
}

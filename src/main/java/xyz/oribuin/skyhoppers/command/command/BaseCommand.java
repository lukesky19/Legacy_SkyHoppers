package xyz.oribuin.skyhoppers.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.ArgumentsDefinition;
import dev.rosewood.rosegarden.command.framework.BaseRoseCommand;
import dev.rosewood.rosegarden.command.framework.CommandInfo;

public class BaseCommand extends BaseRoseCommand {

    public BaseCommand(RosePlugin rosePlugin) {
        super(rosePlugin);
    }

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder("skyhoppers")
                .aliases("sh", "skh")
                .permission("skyhoppers.commands.basecommand")
                .arguments(ArgumentsDefinition.builder()
                        .optionalSub(
                                new ViewCommand(this.rosePlugin),
                                new GiveAllCommand(this.rosePlugin),
                                new GiveCommand(this.rosePlugin),
                                new ReloadCommand(this.rosePlugin)
                        ))
                .build();
    }
}

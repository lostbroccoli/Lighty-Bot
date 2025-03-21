package net.neoooo.modules.slotchanger.listener;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.neoooo.modules.slotchanger.SlotChanger;

import java.util.ArrayList;

public class ButtonInteraction extends ListenerAdapter {


    @Override
    public void onButtonInteraction(net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent event){
        if(event.getGuild() == null) return;

        switch (event.getButton().getId()){
            case "slotchange":
                if(event.getMember().getVoiceState().getChannel() == null){
                    event.reply("You're not in a voicechannel!").setEphemeral(true).queue();
                    return;
                }
                if(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_categories", ArrayList.class).contains(event.getMember().getVoiceState().getChannel().getParentCategoryId()) || SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_channels", ArrayList.class).contains(event.getMember().getVoiceState().getChannel().getId())) {
                    event.reply("Here you cannot change slots!").setEphemeral(true).queue();
                    return;
                }

                TextInput input = TextInput.create("count", "Slotcount", TextInputStyle.SHORT).setMaxLength(2).setRequired(true).setPlaceholder("10").build();
                Modal modal = Modal.create("slotchange", "Change Slots").addActionRow(input).build();

                event.replyModal(modal).queue();
        }
    }
}

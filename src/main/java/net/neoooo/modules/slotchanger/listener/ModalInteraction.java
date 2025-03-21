package net.neoooo.modules.slotchanger.listener;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class ModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event){
        if(event.getGuild() == null) return;

        switch (event.getModalId()){
            case "slotchange":
                String count = event.getValue("count").getAsString();
                if(count.equals("1")) return;

                if(!isNumeric(count)) {
                    event.reply("You're sure you've provided me with a number?").setEphemeral(true).queue();
                    return;
                }

                event.getMember().getVoiceState().getChannel().asVoiceChannel().getManager().setUserLimit(Integer.parseInt(count)).and(event.reply("Successful!").setEphemeral(true)).queue();

        }
    }

    public static boolean isNumeric(String s) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(s, pos);
        return s.length() == pos.getIndex();
    }
}

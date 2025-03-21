package net.neoooo.modules.module_management;

import net.dv8tion.jda.api.entities.Guild;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.slotchanger.SlotChanger;
import net.neoooo.modules.suggestions.Suggestions;
import net.neoooo.modules.ticketsystem.TicketSystem;
import net.neoooo.modules.voicecreate.VoiceCreate;
import net.neoooo.mongodb.MongoDB_Manager;

import java.util.List;
import java.util.Map;

public class ModuleManagement {

    private List<Guild> guilds;
    private Map<String, BotModule> botModules;
    private MongoDB_Manager mongoDBManager = new MongoDB_Manager();

    public ModuleManagement(){}

    public void init(){

        for(String modulename : botModules.keySet()){
            mongoDBManager.getDatabase().createCollection(modulename);
        }

        for(BotModule module : botModules.values()){
            for(Guild guild : guilds){
                if(mongoDBManager.isCollectionEmpty(module.getModulename())){
                    SlotChanger.put(guild.getId());
                    Suggestions.put(guild.getId());
                    TicketSystem.put(guild.getId());
                    VoiceCreate.put(guild.getId());
                }
            }
        }
        System.out.println("Modules are up! Everthing went fine :)");
    }

    public Map<String, BotModule> getBotModules() {
        return botModules;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public void setBotModules(Map<String, BotModule> botModules) {
        this.botModules = botModules;
    }

    public void setGuilds(List<Guild> guilds) {
        this.guilds = guilds;
    }
}

package net.neoooo.modules;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.neoooo.mongodb.MongoDB_Manager;
import org.bson.Document;

public class BotModule {

    private String modulename;
    //private String moduledescription;
    private Object[] listeners;
    private SlashCommandData commands;
    private MongoDB_Manager manager = new MongoDB_Manager();

    public BotModule(){}

    public BotModule(String modulename, Object[] listeners, SlashCommandData commands){
        this.modulename = modulename;
        this.listeners = listeners;
        this.commands = commands;
    }

    public MongoCollection<Document> getCollection(){
        return modulename != null ? manager.getDatabase().getCollection(modulename) : null;
    }

    public MongoDB_Manager getManager(){
        return manager;
    }

    public String getModulename() {
        return modulename;
    }

    public Object[] getListeners() {
        return listeners;
    }

    public SlashCommandData getCommands() {
        return commands;
    }

    public static void put(String serverid){}
}

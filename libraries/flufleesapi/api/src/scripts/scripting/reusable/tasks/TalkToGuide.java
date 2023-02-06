package scripts.scripting.reusable.tasks;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import scripts.game.NPCDialog;
import scripts.scripting.frameworks.task.Priority;
import scripts.scripting.frameworks.task.tasktypes.SuccessTask;

public class TalkToGuide extends SuccessTask {

    private String npcName, status = "Talking to guide";
    private String[] options = new String[0];
    private NPCDialog.ChatOptions chatOptions;

    public TalkToGuide(String npcName) {
        this.npcName = npcName;
    }

    public TalkToGuide(String npcName, NPCDialog.ChatOptions chatOptions) {
        this(npcName);
        this.chatOptions = chatOptions;
    }

    public TalkToGuide(String npcName, String status) {
        this(npcName);
        this.status = status;
    }

    public TalkToGuide(String npcName, String status, NPCDialog.ChatOptions chatOptions) {
        this(npcName, status);
        this.chatOptions = chatOptions;
    }

    public TalkToGuide(String npcName, String[] options) {
        this(npcName);
        this.options = options;
    }

    public TalkToGuide(String npcName, NPCDialog.ChatOptions chatOptions, String... options) {
        this(npcName, chatOptions);
        this.options = options;
    }

    public TalkToGuide(String npcName, String status, String[] options) {
        this(npcName, status);
        this.options = options;
    }

    public TalkToGuide(String npcName, String status, NPCDialog.ChatOptions chatOptions, String... options) {
        this(npcName, status, chatOptions);
        this.options = options;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean isValid() {
        return NPCs.findNearest(npcName).length > 0;
    }

    @Override
    public void successExecute() {
        if (options.length > 0) {
            NPCDialog.handleNPCChat(npcName, NPCDialog.ChatOptions.generateRandom(Player.getRSPlayer().getName()),
                    options);
        } else {
            NPCDialog.handleNPCChat(npcName, NPCDialog.ChatOptions.generateRandom(Player.getRSPlayer().getName()));
        }
    }

    @Override
    public String getStatus() {
        return status;
    }
}

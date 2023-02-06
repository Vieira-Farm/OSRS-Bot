package scripts.scripting.painting.scriptpaint;

import scripts.client.clientextensions.Timing;
import org.tribot.api.Screen;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.Entities;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripts.scripting.listeners.inventoryListener.InventoryObserver;
import scripts.utils.Utilities;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ScriptPaint {

    private static final Color BACKGROUND_COLOR = new Color(55,58,63),
            FLUFFEES_GRADIENT_BLUE = new Color(91, 154, 235);
    private Color mainColor;

    private static final int FLUFFEES_TEXT_HEIGHT = 40,SCRIPT_TITLE_HEIGHT = 20;
    private int numberFields = 0, delay = 0, gpGained = 0, gpHour = 0, xCoord = 15, yCoord;

    private GradientPaint scriptTitleGradient = null, fluffeesTextGradient = null;

    private HashMap<Skills.SKILLS, SkillXP> trackedSkills = new HashMap<>();
    private ArrayList<Map.Entry<String, String>> fields = new ArrayList<>();
    private String scriptName;
    private long lastPaintTime = System.currentTimeMillis();
    private boolean trackProfits = false;
    private InventoryObserver inventoryObserver;

    private ScriptPaint() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("scripts/modules/fluffeesapi/scripting/painting/scriptpaint/fonts/vendetta_font.txt")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("scripts/modules/fluffeesapi/scripting/painting/scriptpaint/fonts/the_bold_font.txt")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("scripts/modules/fluffeesapi/scripting/painting/scriptpaint/fonts/hind_font.txt")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D graphics2D, long runtime) {
        paint(graphics2D, runtime, null);
    }

    public void paint(Graphics2D graphics2D, long runtime, List<AbstractMap.SimpleEntry<String, String>> newFields) {
        int newFieldsSize = newFields != null ? newFields.size() : 0;
        this.numberFields += newFieldsSize;
        initializeGraphics2D(graphics2D);
        drawFluffeesText(graphics2D);
        drawScriptName(graphics2D);
        drawBackgroundInformation(graphics2D);
        int currentFieldNumber = 1;
        drawString(graphics2D, currentFieldNumber++, new AbstractMap.SimpleEntry<>("Runtime", Timing.getTimestamp(runtime)));
        if (delay == 0 || Timing.timeFromMark(lastPaintTime) > delay) {
            performCalculations(runtime);
            lastPaintTime = System.currentTimeMillis();
        }
        for (Map.Entry<String, String> field : this.fields) {
            drawString(graphics2D, currentFieldNumber++, field);
        }
        if (newFields != null) {
            for (Map.Entry<String, String> field : newFields) {
                drawString(graphics2D, currentFieldNumber++, field);
            }
        }
        drawSkills(graphics2D, currentFieldNumber);
        if (trackProfits) {
            drawString(graphics2D, currentFieldNumber++, new AbstractMap.SimpleEntry<>("GP Gained (GP/HR)", gpGained + " (" + gpHour + ")"));
        }
        this.numberFields -= newFieldsSize;
    }

    private void initializeGraphics2D(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    private void drawFluffeesText(Graphics2D graphics2D) {
        Font font = new Font("Vendetta", Font.PLAIN, FLUFFEES_TEXT_HEIGHT);
        graphics2D.setFont(font);
        drawOutline(graphics2D, "Fluffees", BACKGROUND_COLOR, xCoord, yCoord,  font);
        graphics2D.setPaint(fluffeesTextGradient);
        graphics2D.drawString("Fluffees", xCoord,yCoord);
    }

    private void drawScriptName(Graphics2D graphics2D) {
        Font font = new Font("The Bold Font", Font.PLAIN, SCRIPT_TITLE_HEIGHT);
        graphics2D.setFont(font);
        drawOutline(graphics2D, this.scriptName, BACKGROUND_COLOR, xCoord + 90, yCoord + 20, font);
        graphics2D.setPaint(scriptTitleGradient);
        graphics2D.drawString(this.scriptName, xCoord + 90, yCoord + 20);
    }

    private void drawOutline(Graphics2D g, String string, Color backColor, int x, int y, Font font) {
        g.setFont(font);
        g.setColor(backColor);
        FontRenderContext frc = g.getFontRenderContext();
        AffineTransform affineTransform = g.getTransform();
        affineTransform.translate(x, y);
        TextLayout tl = new TextLayout(string, g.getFont(), frc);
        Shape shape = affineTransform.createTransformedShape(tl.getOutline(null));
        g.setStroke(new BasicStroke(2));
        g.draw(shape);
    }

    private void drawBackgroundInformation(Graphics2D graphics2D) {
        graphics2D.setColor(BACKGROUND_COLOR);
        graphics2D.fillRoundRect(xCoord, yCoord + SCRIPT_TITLE_HEIGHT + 5, 200, 10 + (numberFields + 1) * 20, 5, 5);
        graphics2D.setColor(mainColor);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawRoundRect(xCoord, yCoord + SCRIPT_TITLE_HEIGHT + 5, 200,10 + (numberFields + 1) * 20, 5, 5);
    }

    private void drawString(Graphics2D graphics2D, int currentFieldNumber, Map.Entry<String, String> text) {
        graphics2D.setFont(new Font("Hind", Font.PLAIN, 15));
        graphics2D.setColor(mainColor);
        graphics2D.drawString(text.getKey() + ": ", xCoord + 10, yCoord + 25 + (20 * currentFieldNumber));
        graphics2D.drawString(text.getKey() + ": ", xCoord + 10, yCoord + 25 + (20 * currentFieldNumber));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text.getValue(), xCoord + 10 + graphics2D.getFontMetrics().stringWidth(text.getKey() + ": "), yCoord + 25 + (20 * currentFieldNumber));
    }

    public void stopInventoryObserver() {
        this.inventoryObserver.end();
    }

    private void performCalculations(long runtime) {
        for (Skills.SKILLS skill : trackedSkills.keySet()) {
            trackedSkills.get(skill).setXpGained(skill.getXP() - trackedSkills.get(skill).getStartingXP());
            trackedSkills.get(skill).setXpHour(Utilities.getAmountPerHour(trackedSkills.get(skill).getXpGained(), runtime));
        }
        if (trackProfits) {
            gpGained = inventoryObserver.getGpEarned();
            gpHour = Utilities.getAmountPerHour(inventoryObserver.getGpEarned(), runtime);
        }
    }

    private void drawSkills(Graphics2D graphics2D, int currentFieldNumber) {
        for (Skills.SKILLS skill : trackedSkills.keySet()) {
            drawString(graphics2D, currentFieldNumber,  new AbstractMap.SimpleEntry<>(
                skill.name().substring(0, 1) + skill.name().substring(1).toLowerCase() + " XP (XP/HR)",
                trackedSkills.get(skill).getXpGained() + " (" + trackedSkills.get(skill).getXpHour() + ")"));
            currentFieldNumber++;
        }
    }

    private static GradientPaint createScriptTitlePaint (int xCoord, int yCoord, String scriptName, Color mainColor) {
        return new GradientPaint(xCoord + 90, yCoord + 20, mainColor, xCoord + 90, 320 -
                (float) (SCRIPT_TITLE_HEIGHT * 0.70), Color.WHITE, false);
    }

    private static GradientPaint createFluffeesTextGradient (int xCoord, int yCoord) {
        return new GradientPaint(xCoord, yCoord, FLUFFEES_GRADIENT_BLUE, xCoord, yCoord -
                (float) (FLUFFEES_TEXT_HEIGHT * 0.67), Color.WHITE, false);
    }

    public void setField(int index, String key, String value) {
        fields.set(index, new AbstractMap.SimpleEntry<>(key, value));
    }

    public void setField(int index, Map.Entry<String, String> fieldContents) {
        fields.set(index, fieldContents);
    }

    public static Color hex2Rgb(String colorStr) {
        if (colorStr.length() < 7) {
            colorStr = "#" + colorStr;
        }
        return new Color(
                Integer.valueOf(colorStr.substring(1,3), 16),
                Integer.valueOf(colorStr.substring(3,5), 16),
                Integer.valueOf(colorStr.substring(5,7), 16));
    }

    @SuppressWarnings("unused")
    public static class Builder {

        private Color mainColor;
        private HashMap<Skills.SKILLS, SkillXP> trackedSkills = new HashMap<>();
        private ArrayList<Map.Entry<String, String>> fields = new ArrayList<>();
        private String scriptName;
        private int numberFields = 0, delay = 0, xCoord = 15, yCoord = 300;
        private boolean trackProfits = false;

        public Builder(Color mainColor, String scriptName) {
            this.mainColor = mainColor;
            this.scriptName = scriptName;
        }

        public Builder addSkill(Skills.SKILLS skill) {
            this.trackedSkills.put(skill, new SkillXP.SkillXPBuilder().setStartingXP(skill.getXP()).build());
            this.numberFields++;
            return this;
        }

        public Builder withDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder addField(String key, String value) {
            return addField(new AbstractMap.SimpleEntry<>(key, value));
        }

        public Builder addField(Map.Entry<String, String> fieldContents) {
            this.fields.add(fieldContents);
            this.numberFields++;
            return this;
        }

        public Builder addProfitTracking() {
            this.trackProfits = true;
            return this;
        }

        public Builder setXCoord(int xCoord) {
            this.xCoord = xCoord;
            return this;
        }

        public Builder setYCoord(int yCoord) {
            this.yCoord = yCoord;
            return this;
        }

        public ScriptPaint build() {
            ScriptPaint scriptPaint = new ScriptPaint();
            if (Login.getLoginState() == Login.STATE.INGAME) {
                RSInterface chatbox = Entities.find(InterfaceEntity::new).inMaster(162).viewportHeightGreaterThan(1).getFirstResult();
                Rectangle chatboxBounds = chatbox != null ? chatbox.getAbsoluteBounds() : null;
                if (chatboxBounds != null) {
                    this.yCoord = (int) (chatboxBounds.getY() - chatboxBounds.getHeight()) + 90;
                    this.xCoord = (int) (chatboxBounds.getX());
                }
            } else {
                this.yCoord = (int) Screen.getDimension().getHeight() - 185;
            }
            scriptPaint.xCoord = this.xCoord;
            scriptPaint.yCoord = this.yCoord;
            scriptPaint.mainColor = this.mainColor;
            scriptPaint.scriptName = this.scriptName;
            scriptPaint.scriptTitleGradient = createScriptTitlePaint(this.xCoord, this.yCoord, this.scriptName, this.mainColor);
            scriptPaint.fluffeesTextGradient = createFluffeesTextGradient(this.xCoord, this.yCoord);
            scriptPaint.trackedSkills = this.trackedSkills;
            scriptPaint.fields = this.fields;
            scriptPaint.numberFields = this.numberFields;
            scriptPaint.delay = this.delay;
            scriptPaint.trackProfits = this.trackProfits;
            if (trackProfits) {
                scriptPaint.inventoryObserver = new InventoryObserver(true);
                scriptPaint.inventoryObserver.start();
            }
            return scriptPaint;
        }
    }
}

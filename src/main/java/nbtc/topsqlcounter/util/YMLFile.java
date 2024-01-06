package nbtc.topsqlcounter.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class YMLFile {
    public String path;

    private final File file;

    private YamlConfiguration cfg;

    public YMLFile(String name, String path) {
        this.path = path;
        (new File(path)).mkdir();
        this.file = new File(path, name);
    }

    public void createNew() {
        if (!this.file.exists())
            try {
                this.file.createNewFile();
                this.cfg = YamlConfiguration.loadConfiguration(this.file);
                setDefaults();
            } catch (Exception exception) {
            }
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void delete() {
        this.file.delete();
        this.cfg = null;
    }

    public YMLFile(String name) {
        (new File(this.path)).mkdir();
        this.file = new File(this.path, name);
        if (!this.file.exists())
            try {
                this.file.createNewFile();
                this.cfg = YamlConfiguration.loadConfiguration(this.file);
                setDefaults();
            } catch (Exception exception) {
            }
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
    }

    public abstract void setDefaults();

    public FileConfiguration getManager() {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        return (FileConfiguration) this.cfg;
    }

    public void setValue(String path, int value) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        this.cfg.set(path, Integer.valueOf(value));
        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setObject(String path, Object object) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        this.cfg.set(path, object);
        save();
    }

    public void setStringList(String path, List<String> list) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        cfg.set(path, list);
        save();
    }
    public void setIntegerList(String path, List<Integer> list) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        cfg.set(path, list);
        save();
    }

    public List<String> getStringList(String path) {
        List<String> newList = new ArrayList<>();
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        for (String text : this.cfg.getStringList(path)) {
            newList.add(ChatColor.translateAlternateColorCodes('&', text));
        }
        return newList;
    }

    public Object getObject(String path) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        return this.cfg.get(path);
    }

    public int getValue(String path) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        return this.cfg.getInt(path);
    }

    public String getString(String path) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        return this.cfg.getString(path);
    }

    public void setString(String path, String value) {
        if (this.cfg == null)
            this.cfg = YamlConfiguration.loadConfiguration(this.file);
        this.cfg.set(path, value);
        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }
}

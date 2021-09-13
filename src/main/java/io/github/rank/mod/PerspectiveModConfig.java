package io.github.rank.mod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.LiteralText;

@Config(name = "perspectivemod")
public class PerspectiveModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("main")
    @ConfigEntry.Gui.TransitiveObject
    public CategoryMain main = new CategoryMain();

    @Config(name = "main")
    public static class CategoryMain implements ConfigData {
        public boolean isOn = false;
        public boolean holdMode = false;
    }

    @SuppressWarnings("deprecation")
    public static Screen getConfigScreen(Screen parent) {
        try {
            ConfigScreenProvider<PerspectiveModConfig> provider = (ConfigScreenProvider<PerspectiveModConfig>) AutoConfig.getConfigScreen(PerspectiveModConfig.class, parent);
            provider.setI13nFunction(manager -> "config.perspectivemod");
            provider.setOptionFunction((baseI13n, field) -> String.format("%s.%s", baseI13n, field.getName()));
            provider.setCategoryFunction((baseI13n, categoryName) -> String.format("%s.%s", baseI13n, categoryName));

            return provider.get();
        } catch (Exception e) {
            e.printStackTrace();
            ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
            toastManager.add(new SystemToast(SystemToast.Type.PACK_LOAD_FAILURE, new LiteralText("Error loading screen"), new LiteralText("Check console for details.")));
        }

        return new Screen(new LiteralText("")) {
            @Override
            protected void init() {
                assert this.client != null;
                this.client.setScreen(parent);
            }
        };
    }
}
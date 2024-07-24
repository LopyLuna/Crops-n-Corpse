package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.SoundRegistry;

public class ModSoundProvider extends SoundDefinitionsProvider {
    public ModSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, CNCMod.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(SoundRegistry.PEASHOOTER_DEATH, SoundDefinition.definition()
                .subtitle("subtitles.cnc.peashooter_death")
                .with(sound(CNCMod.rl("peashooter_death_1")),
                        sound(CNCMod.rl("peashooter_death_2"))));
        add(SoundRegistry.PEASHOOTER_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.peashooter_hurt")
                .with(sound(CNCMod.rl("peashooter_hurt_1")),
                        sound(CNCMod.rl("peashooter_hurt_2")),
                        sound(CNCMod.rl("peashooter_hurt_3")),
                        sound(CNCMod.rl("peashooter_hurt_4")),
                        sound(CNCMod.rl("peashooter_hurt_5"))));
        add(SoundRegistry.PEASHOOTER_SHOOT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.peashooter_shoot")
                .with(sound(CNCMod.rl("peashooter_shoot"))));
        add(SoundRegistry.PLANT_SPAWN, SoundDefinition.definition()
                .subtitle("subtitles.cnc.plant_spawn")
                .with(sound(CNCMod.rl("plant_spawn"))));
    }
}

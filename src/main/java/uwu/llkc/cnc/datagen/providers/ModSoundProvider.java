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
        add(SoundRegistry.SUNFLOWER_DEATH, SoundDefinition.definition()
                .subtitle("subtitles.cnc.sunflower_death")
                .with(sound(CNCMod.rl("sunflower_death_1")),
                        sound(CNCMod.rl("sunflower_death_2"))));
        add(SoundRegistry.SUNFLOWER_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.sunflower_hurt")
                .with(sound(CNCMod.rl("sunflower_hurt_1")),
                        sound(CNCMod.rl("sunflower_hurt_2")),
                        sound(CNCMod.rl("sunflower_hurt_3"))));
        add(SoundRegistry.SUNFLOWER_PRODUCE, SoundDefinition.definition()
                .subtitle("subtitles.cnc.sunflower_produce")
                .with(sound(CNCMod.rl("sunflower_produce_1")),
                        sound(CNCMod.rl("sunflower_produce_2"))));
        add(SoundRegistry.BROWNCOAT_AMBIANCE, SoundDefinition.definition()
                .subtitle("subtitles.cnc.browncoat_ambiance")
                .with(sound(CNCMod.rl("browncoat_ambiance_1")),
                        sound(CNCMod.rl("browncoat_ambiance_2")),
                        sound(CNCMod.rl("browncoat_ambiance_3"))));
        add(SoundRegistry.BROWNCOAT_ATTACK, SoundDefinition.definition()
                .subtitle("subtitles.cnc.browncoat_attack")
                .with(sound(CNCMod.rl("browncoat_attack_1")),
                        sound(CNCMod.rl("browncoat_attack_2"))));
        add(SoundRegistry.BROWNCOAT_DEATH, SoundDefinition.definition()
                .subtitle("subtitles.cnc.browncoat_death")
                .with(sound(CNCMod.rl("browncoat_death_1")),
                        sound(CNCMod.rl("browncoat_death_2")),
                        sound(CNCMod.rl("browncoat_death_3"))));
        add(SoundRegistry.BROWNCOAT_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.browncoat_hurt")
                .with(sound(CNCMod.rl("browncoat_hurt_1")),
                        sound(CNCMod.rl("browncoat_hurt_2")),
                        sound(CNCMod.rl("browncoat_hurt_3")),
                        sound(CNCMod.rl("browncoat_hurt_4")),
                        sound(CNCMod.rl("browncoat_hurt_5"))));
        add(SoundRegistry.WALL_NUT_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.wall_nut_hurt")
                .with(sound(CNCMod.rl("wall_nut_hurt_1")))
                .with(sound(CNCMod.rl("wall_nut_hurt_2"))));
        add(SoundRegistry.WALL_NUT_ARMOR_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.wall_nut_armor_hurt")
                .with(sound(CNCMod.rl("wall_nut_armor_hurt_1")))
                .with(sound(CNCMod.rl("wall_nut_armor_hurt_2")))
                .with(sound(CNCMod.rl("wall_nut_armor_hurt_3"))));
        add(SoundRegistry.WALL_NUT_DEATH, SoundDefinition.definition()
                .subtitle("subtitles.cnc.wall_nut_death")
                .with(sound(CNCMod.rl("wall_nut_death"))));
        add(SoundRegistry.IMP_ATTACK, SoundDefinition.definition()
                .subtitle("subtitles.cnc.imp_attack")
                .with(sound(CNCMod.rl("imp_attack"))));
        add(SoundRegistry.IMP_DEATH, SoundDefinition.definition()
                .subtitle("subtitles.cnc.imp_death")
                .with(sound(CNCMod.rl("imp_death_1")))
                .with(sound(CNCMod.rl("imp_death_2"))));
        add(SoundRegistry.IMP_HURT, SoundDefinition.definition()
                .subtitle("subtitles.cnc.imp_hurt")
                .with(sound(CNCMod.rl("imp_hurt_1")))
                .with(sound(CNCMod.rl("imp_hurt_2"))));
        add(SoundRegistry.IMP_IDLE, SoundDefinition.definition()
                .subtitle("subtitles.cnc.imp_idle")
                .with(sound(CNCMod.rl("imp_idle_1")))
                .with(sound(CNCMod.rl("imp_idle_2")))
                .with(sound(CNCMod.rl("imp_idle_3"))));
    }
}

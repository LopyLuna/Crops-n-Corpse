package uwu.llkc.cnc.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.datagen.providers.ModEntityTagProvider;
import uwu.llkc.cnc.datagen.providers.ModItemModelProvider;
import uwu.llkc.cnc.datagen.providers.ModLanguageProvider;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DatagenHandler {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLanguageProvider(output));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTagProvider(output, event.getLookupProvider(), existingFileHelper));
    }
}

package uwu.llkc.cnc.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import uwu.llkc.cnc.registry.ModItems;
import uwu.llkc.cnc.util.ModUtils;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.LOGO);
    }

    private void basicItem(Supplier<? extends Item> item) {
        super.basicItem(item.get());
    }

    private ItemModelBuilder basicItem(DeferredHolder<Item, ? extends Item> item, UnaryOperator<ResourceLocation> modelLocationModifier) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");

        return getBuilder(modelLocationModifier.apply(name).getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ModUtils.location(modelLocationModifier.apply(name).getPath()));
    }
}

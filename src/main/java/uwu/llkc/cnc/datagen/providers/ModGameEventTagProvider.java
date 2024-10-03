package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGameEventTagProvider extends GameEventTagsProvider {
    static final List<ResourceKey<GameEvent>> VIBRATIONS_EXCEPT_FLAP = List.of(
            GameEvent.BLOCK_ATTACH.key(),
            GameEvent.BLOCK_CHANGE.key(),
            GameEvent.BLOCK_CLOSE.key(),
            GameEvent.BLOCK_DESTROY.key(),
            GameEvent.BLOCK_DETACH.key(),
            GameEvent.BLOCK_OPEN.key(),
            GameEvent.BLOCK_PLACE.key(),
            GameEvent.BLOCK_ACTIVATE.key(),
            GameEvent.BLOCK_DEACTIVATE.key(),
            GameEvent.CONTAINER_CLOSE.key(),
            GameEvent.CONTAINER_OPEN.key(),
            GameEvent.DRINK.key(),
            GameEvent.EAT.key(),
            GameEvent.ELYTRA_GLIDE.key(),
            GameEvent.ENTITY_DAMAGE.key(),
            GameEvent.ENTITY_DIE.key(),
            GameEvent.ENTITY_DISMOUNT.key(),
            GameEvent.ENTITY_INTERACT.key(),
            GameEvent.ENTITY_MOUNT.key(),
            GameEvent.ENTITY_PLACE.key(),
            GameEvent.ENTITY_ACTION.key(),
            GameEvent.EQUIP.key(),
            GameEvent.EXPLODE.key(),
            GameEvent.FLUID_PICKUP.key(),
            GameEvent.FLUID_PLACE.key(),
            GameEvent.HIT_GROUND.key(),
            GameEvent.INSTRUMENT_PLAY.key(),
            GameEvent.ITEM_INTERACT_FINISH.key(),
            GameEvent.LIGHTNING_STRIKE.key(),
            GameEvent.NOTE_BLOCK_PLAY.key(),
            GameEvent.PRIME_FUSE.key(),
            GameEvent.PROJECTILE_LAND.key(),
            GameEvent.PROJECTILE_SHOOT.key(),
            GameEvent.SHEAR.key(),
            GameEvent.SPLASH.key(),
            GameEvent.STEP.key(),
            GameEvent.SWIM.key(),
            GameEvent.TELEPORT.key(),
            GameEvent.UNEQUIP.key()
    );

    public ModGameEventTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(Tags.gameEvents.CAN_CHERRY_BOMB_LISTEN)
                .addAll(VIBRATIONS_EXCEPT_FLAP)
                .addAll(VibrationSystem.RESONANCE_EVENTS)
                .add(GameEvent.SHRIEK.key())
                .addTag(GameEventTags.SHRIEKER_CAN_LISTEN);
    }
}

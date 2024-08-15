package uwu.llkc.cnc.common.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = CNCMod.MOD_ID)
public class NeoForgeEvents {
    @SubscribeEvent
    public static void addCustomTrades(final VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ItemRegistry.PEA_POD.get(), pRandom.nextIntBetweenInclusive(12, 17)),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(ItemRegistry.SUNFLOWER_SEEDS.get(), pRandom.nextIntBetweenInclusive(13, 18)),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.02f));
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(final WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 1),
                new ItemStack(ItemRegistry.PEA_POD.get(), 3),
                16, 2, 0.1f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 1),
                new ItemStack(ItemRegistry.SUNFLOWER_SEEDS.get(), 5),
                16, 2, 0.1f));

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 12),
                new ItemStack(ItemRegistry.EMPTY_SEED_PACKET.get(), 1),
                2, 2, 0.1f));
    }

    @SubscribeEvent
    public static void livingDamageEvent(final LivingDamageEvent.Pre event) {
        ItemStack item = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
        if (item.is(Items.BUCKET) || item.is(ItemRegistry.TRAFFIC_CONE)) {
            int durability = item.getMaxDamage() - item.getDamageValue();
            if (durability < event.getOriginalDamage()) {
                item.hurtAndBreak(durability, event.getEntity(), EquipmentSlot.HEAD);
                event.setNewDamage(event.getOriginalDamage() - durability);
            } else {
                item.hurtAndBreak(((int) event.getOriginalDamage()), event.getEntity(), EquipmentSlot.HEAD);
                event.setNewDamage(0);
            }
        }
    }
}

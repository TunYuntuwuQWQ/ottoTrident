package nya.tuyw.ottotrident;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(ottoTrident.MODID)
public class ottoTrident {
    public static final String MODID = "ottotrident";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,ottoTrident.MODID);
    public static final RegistryObject<SoundEvent> WAAH = SOUND_EVENTS.register
            ("sound_waah",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ottoTrident.MODID,"sound_waah")));
    public ottoTrident() {
        SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

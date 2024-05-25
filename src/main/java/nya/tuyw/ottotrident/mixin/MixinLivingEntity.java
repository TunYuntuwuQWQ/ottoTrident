package nya.tuyw.ottotrident.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import nya.tuyw.ottotrident.ottoTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity{
    @Shadow protected abstract void doAutoAttackOnTouch(LivingEntity p_21277_);

    @Shadow protected int autoSpinAttackTicks;

    @Shadow protected abstract void setLivingEntityFlag(int p_21156_, boolean p_21157_);

    public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Redirect(method = "aiStep",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;checkAutoSpinAttack(Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/phys/AABB;)V"))
    protected void checkAutoSpinAttack(LivingEntity instance, AABB ab, AABB aabb){
        AABB AABB = ab.minmax(aabb);
        List<Entity> list = this.level().getEntities(this, AABB);
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof LivingEntity) {
                    this.doAutoAttackOnTouch((LivingEntity) entity);
                    this.autoSpinAttackTicks = 0;
                    Player player = Minecraft.getInstance().player;
                    float f7 = player.getYRot();
                    float f = player.getXRot();
                    float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                    float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
                    float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                    float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    float f5 = 5.0F;
                    f1 *= f5 / f4 ;
                    f2 *= f5 / f4 ;
                    f3 *= f5 / f4 ;
                    entity.push(f1, f2, f3);
                    this.setDeltaMovement(this.getDeltaMovement().scale(-0.2D));
                    entity.playSound(ottoTrident.WAAH.get(),1.3f,1.3f);
                    sendSystemMessage(Component.translatable("message.ottotrident.waaah"));
                    break;
                }
            }
        } else if (this.horizontalCollision) {
            this.autoSpinAttackTicks = 0;
        }

        if (!this.level().isClientSide && this.autoSpinAttackTicks <= 0) {
            this.setLivingEntityFlag(4, false);
        }

    }
}

package com.github.patrick.customentity.renderer;

import com.github.patrick.customentity.client.CustomEntity;
import com.github.patrick.customentity.client.CustomEntityManager;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderArmorStand extends RenderLivingBase<EntityArmorStand>
{
    public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");

    public RenderArmorStand(RenderManager manager)
    {
        super(manager, new ModelArmorStand(), 0.0F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelArmorStandArmor(0.5F);
                this.modelArmor = new ModelArmorStandArmor(1.0F);
            }
        };
        this.addLayer(layerbipedarmor);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerElytra(this));
        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }

    protected ResourceLocation getEntityTexture(EntityArmorStand entity)
    {
        return TEXTURE_ARMOR_STAND;
    }

    public ModelArmorStand getMainModel()
    {
        return (ModelArmorStand)super.getMainModel();
    }

    protected void applyRotations(EntityArmorStand entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        float f = (float)(entityLiving.world.getTotalWorldTime() - entityLiving.punchCooldown) + partialTicks;

        if (f < 5.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f / 1.5F * (float)Math.PI) * 3.0F, 0.0F, 1.0F, 0.0F);
        }
    }

    protected boolean canRenderName(EntityArmorStand entity)
    {
        return entity.getAlwaysRenderNameTag();
    }

    public void doRender(EntityArmorStand entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.hasMarker())
        {
            this.renderMarker = true;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (entity.hasMarker())
        {
            this.renderMarker = false;
        }
    }

    @Override
    protected void preRenderCallback(EntityArmorStand entitylivingbaseIn, float partialTickTime) {
        CustomEntity custom = CustomEntityManager.getOrCreateInstance().getCustomEntity(entitylivingbaseIn.getEntityId());
        if(custom != null) {
            custom.applyGraphic(entitylivingbaseIn);
        } else {
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }
    }
}

package com.grillo78.littlecritters.client.entities.model;

import com.google.common.collect.ImmutableList;
import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import net.minecraft.client.renderer.entity.model.ModelUtils;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Firefly - Anomalocaris101 Created using Tabula 7.0.1
 */

@OnlyIn(Dist.CLIENT)
public class FireFlyModel<T extends FireFlyEntity> extends SegmentedModel<T> {
    public ModelRenderer Body;
    public ModelRenderer Antenne;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer LWingcase;
    public ModelRenderer RWingcase;
    public ModelRenderer LWing;
    public ModelRenderer RWing;
    public ModelRenderer Bum;
    private float bodyPitch;

    public FireFlyModel() {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 19.5F, 0.0F);
        Body.setTextureOffset(3, 3).addBox(-3.0F, -1.5F, -4.0F, 6.0F, 3.0F, 5.0F, 0.0F, false);

        Leg3 = new ModelRenderer(this);
        Leg3.setRotationPoint(0.0F, 1.5F, 0.0F);
        Body.addChild(Leg3);
        setRotationAngle(Leg3, 0.7854F, 0.0F, 0.0F);
        Leg3.setTextureOffset(0, 24).addBox(1.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, false);
        Leg3.setTextureOffset(0, 24).addBox(-2.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, true);

        Bum = new ModelRenderer(this);
        Bum.setRotationPoint(0.0F, -0.5F, 1.0F);
        Body.addChild(Bum);
        Bum.setTextureOffset(25, 8).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);

        LWing = new ModelRenderer(this);
        LWing.setRotationPoint(1.0F, -1.5F, -1.0F);
        Body.addChild(LWing);
        setRotationAngle(LWing, 0.0F, 0.0F, -0.3927F);
        LWing.setTextureOffset(0, 19).addBox(0.0F, 0.0F, 0.0F, 6.0F, 1.0F, 4.0F, 0.0F, false);

        Antenne = new ModelRenderer(this);
        Antenne.setRotationPoint(0.0F, -0.5F, -4.0F);
        Body.addChild(Antenne);
        setRotationAngle(Antenne, -0.7854F, 0.0F, 0.0F);
        Antenne.setTextureOffset(20, 0).addBox(-3.0F, 0.0F, -5.0F, 6.0F, 1.0F, 5.0F, 0.0F, false);

        RWing = new ModelRenderer(this);
        RWing.setRotationPoint(-1.0F, -1.5F, -1.0F);
        Body.addChild(RWing);
        setRotationAngle(RWing, 0.0F, 0.0F, 0.3927F);
        RWing.setTextureOffset(0, 19).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 1.0F, 4.0F, 0.0F, true);

        LWingcase = new ModelRenderer(this);
        LWingcase.setRotationPoint(0.0F, -1.5F, -2.0F);
        Body.addChild(LWingcase);
        setRotationAngle(LWingcase, 0.0F, -1.5708F, -0.7854F);
        LWingcase.setTextureOffset(0, 11).addBox(0.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);

        RWingcase = new ModelRenderer(this);
        RWingcase.setRotationPoint(0.0F, -1.5F, -2.0F);
        Body.addChild(RWingcase);
        setRotationAngle(RWingcase, 0.0F, 1.5708F, 0.8196F);
        RWingcase.setTextureOffset(0, 11).addBox(-3.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, 0.0F, true);

        Leg2 = new ModelRenderer(this);
        Leg2.setRotationPoint(0.0F, 1.5F, -1.0F);
        Body.addChild(Leg2);
        setRotationAngle(Leg2, 0.3927F, 0.0F, 0.0F);
        Leg2.setTextureOffset(0, 24).addBox(1.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, false);
        Leg2.setTextureOffset(0, 24).addBox(-2.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, true);

        Leg1 = new ModelRenderer(this);
        Leg1.setRotationPoint(0.0F, 1.5F, -2.0F);
        Body.addChild(Leg1);
        setRotationAngle(Leg1, 0.2051F, 0.0F, 0.0F);
        Leg1.setTextureOffset(0, 24).addBox(1.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, false);
        Leg1.setTextureOffset(0, 24).addBox(-2.0F, -0.15F, 0.0F, 1.0F, 3.0F, 1.0F, -0.15F, true);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.bodyPitch = entityIn.getPitch(partialTick);
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.Body);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch) {
        this.RWing.rotateAngleX = 0.0F;
        this.Antenne.rotateAngleX = 0.0F;
        this.Body.rotateAngleX = 0.0F;
        this.Body.rotationPointY = 19.0F;// 19
        boolean flag = entityIn.isOnGround() && entityIn.getMotion().lengthSquared() < 1.0E-7D;

        if (entityIn.getAttachmentPos() == null) {
            if (flag) {
                this.RWing.rotateAngleY = -0.2618F;
                this.RWing.rotateAngleZ = 0.0F;
                this.LWing.rotateAngleX = 0.0F;
                this.LWing.rotateAngleY = 0.2618F;
                this.LWing.rotateAngleZ = 0.0F;
                this.Leg1.rotateAngleX = 0.0F;
                this.Leg2.rotateAngleX = 0.0F;
                this.Leg3.rotateAngleX = 0.0F;
            } else {
                float f = ageInTicks * 2.1F;
                this.RWing.rotateAngleY = 0.0F;
                this.RWing.rotateAngleZ = MathHelper.cos(f) * (float) Math.PI * 0.15F;
                this.LWing.rotateAngleX = this.RWing.rotateAngleX;
                this.LWing.rotateAngleY = this.RWing.rotateAngleY;
                this.LWing.rotateAngleZ = -this.RWing.rotateAngleZ;
                this.Leg1.rotateAngleX = ((float) Math.PI / 4F);
                this.Leg2.rotateAngleX = ((float) Math.PI / 4F);
                this.Leg3.rotateAngleX = ((float) Math.PI / 4F);
                this.Body.rotateAngleX = 0.0F;
                this.Body.rotateAngleY = 0.0F;
                this.Body.rotateAngleZ = 0.0F;
            }
            this.Body.rotateAngleX = 0.0F;
            this.Body.rotateAngleY = 0.0F;
            this.Body.rotateAngleZ = 0.0F;
            if (!flag) {
                float f1 = MathHelper.cos(ageInTicks * 0.18F);
                this.Body.rotateAngleX = 0.1F + f1 * (float) Math.PI * 0.025F;
                this.Antenne.rotateAngleX = f1 * (float) Math.PI * 0.03F;
                this.Leg1.rotateAngleX = -f1 * (float) Math.PI * 0.1F + ((float) Math.PI / 8F);
                this.Leg3.rotateAngleX = -f1 * (float) Math.PI * 0.05F + ((float) Math.PI / 4F);
                this.Body.rotationPointY = 19.0F - MathHelper.cos(ageInTicks * 0.18F) * 0.9F;
            }

            if (this.bodyPitch > 0.0F) {
                this.Body.rotateAngleX = ModelUtils.func_228283_a_(this.Body.rotateAngleX, 3.0915928F, this.bodyPitch);
            }

            this.Leg1.showModel = true;
            this.Leg2.showModel = true;
            this.Leg3.showModel = true;
        }
    }

}

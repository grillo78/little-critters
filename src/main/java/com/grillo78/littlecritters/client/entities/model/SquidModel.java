package com.grillo78.littlecritters.client.entities.model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SquidModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer tentacle1;
	private final ModelRenderer tentacle2;
	private final ModelRenderer tentacle3;
	private final ModelRenderer tentacle4;
	private final ModelRenderer tentacle5;
	private final ModelRenderer tentacle6;
	private final ModelRenderer tentacle7;
	private final ModelRenderer tentacle8;

	public SquidModel() {
		textureWidth = 48;
		textureHeight = 48;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 7.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false);
		body.setTextureOffset(0, 20).addBox(-7.0F, -12.0F, 0.0F, 14.0F, 14.0F, 0.0F, 0.0F, false);

		tentacle1 = new ModelRenderer(this);
		tentacle1.setRotationPoint(3.0F, 8.0F, 0.0F);
		body.addChild(tentacle1);
		setRotationAngle(tentacle1, 0.0F, 1.5708F, 0.0F);
		tentacle1.setTextureOffset(28, 24).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		tentacle1.setTextureOffset(0, 0).addBox(-1.5F, 17.0F, 0.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);

		tentacle2 = new ModelRenderer(this);
		tentacle2.setRotationPoint(3.5F, 8.0F, 3.5F);
		body.addChild(tentacle2);
		setRotationAngle(tentacle2, 0.0F, 0.7854F, 0.0F);
		tentacle2.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle3 = new ModelRenderer(this);
		tentacle3.setRotationPoint(0.0F, 8.0F, 3.5F);
		body.addChild(tentacle3);
		tentacle3.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -0.51F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle4 = new ModelRenderer(this);
		tentacle4.setRotationPoint(-3.0F, 8.0F, 3.0F);
		body.addChild(tentacle4);
		setRotationAngle(tentacle4, 0.0F, -0.7854F, 0.0F);
		tentacle4.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle5 = new ModelRenderer(this);
		tentacle5.setRotationPoint(-3.0F, 8.0F, 0.0F);
		body.addChild(tentacle5);
		setRotationAngle(tentacle5, 0.0F, -1.5708F, 0.0F);
		tentacle5.setTextureOffset(28, 24).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		tentacle5.setTextureOffset(0, 0).addBox(-1.5F, 17.0F, 0.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);

		tentacle6 = new ModelRenderer(this);
		tentacle6.setRotationPoint(-3.5F, 8.0F, -3.5F);
		body.addChild(tentacle6);
		setRotationAngle(tentacle6, 0.0F, -2.3562F, 0.0F);
		tentacle6.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle7 = new ModelRenderer(this);
		tentacle7.setRotationPoint(0.0F, 8.0F, -3.0F);
		body.addChild(tentacle7);
		setRotationAngle(tentacle7, 0.0F, -3.1416F, 0.0F);
		tentacle7.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle8 = new ModelRenderer(this);
		tentacle8.setRotationPoint(3.5F, 8.0F, -3.5F);
		body.addChild(tentacle8);
		setRotationAngle(tentacle8, 0.0F, -3.927F, 0.0F);
		tentacle8.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		tentacle1.rotateAngleX = ageInTicks;
		tentacle2.rotateAngleX = ageInTicks;
		tentacle3.rotateAngleX = ageInTicks;
		tentacle4.rotateAngleX = ageInTicks;
		tentacle5.rotateAngleX = ageInTicks;
		tentacle6.rotateAngleX = ageInTicks;
		tentacle7.rotateAngleX = ageInTicks;
		tentacle8.rotateAngleX = ageInTicks;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
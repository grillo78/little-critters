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
		texWidth = 48;
		texHeight = 48;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 7.0F, 0.0F);
		body.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false);
		body.texOffs(0, 20).addBox(-7.0F, -12.0F, 0.0F, 14.0F, 14.0F, 0.0F, 0.0F, false);

		tentacle1 = new ModelRenderer(this);
		tentacle1.setPos(3.0F, 8.0F, 0.0F);
		body.addChild(tentacle1);
		setRotationAngle(tentacle1, 0.0F, 1.5708F, 0.0F);
		tentacle1.texOffs(28, 24).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		tentacle1.texOffs(0, 0).addBox(-1.5F, 17.0F, 0.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);

		tentacle2 = new ModelRenderer(this);
		tentacle2.setPos(3.5F, 8.0F, 3.5F);
		body.addChild(tentacle2);
		setRotationAngle(tentacle2, 0.0F, 0.7854F, 0.0F);
		tentacle2.texOffs(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle3 = new ModelRenderer(this);
		tentacle3.setPos(0.0F, 8.0F, 3.5F);
		body.addChild(tentacle3);
		tentacle3.texOffs(0, 34).addBox(-0.5F, -1.0F, -0.51F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle4 = new ModelRenderer(this);
		tentacle4.setPos(-3.0F, 8.0F, 3.0F);
		body.addChild(tentacle4);
		setRotationAngle(tentacle4, 0.0F, -0.7854F, 0.0F);
		tentacle4.texOffs(0, 34).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle5 = new ModelRenderer(this);
		tentacle5.setPos(-3.0F, 8.0F, 0.0F);
		body.addChild(tentacle5);
		setRotationAngle(tentacle5, 0.0F, -1.5708F, 0.0F);
		tentacle5.texOffs(28, 24).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		tentacle5.texOffs(0, 0).addBox(-1.5F, 17.0F, 0.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);

		tentacle6 = new ModelRenderer(this);
		tentacle6.setPos(-3.5F, 8.0F, -3.5F);
		body.addChild(tentacle6);
		setRotationAngle(tentacle6, 0.0F, -2.3562F, 0.0F);
		tentacle6.texOffs(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle7 = new ModelRenderer(this);
		tentacle7.setPos(0.0F, 8.0F, -3.0F);
		body.addChild(tentacle7);
		setRotationAngle(tentacle7, 0.0F, -3.1416F, 0.0F);
		tentacle7.texOffs(0, 34).addBox(-0.5F, -1.0F, -0.01F, 1.0F, 10.0F, 1.0F, 0.0F, false);

		tentacle8 = new ModelRenderer(this);
		tentacle8.setPos(3.5F, 8.0F, -3.5F);
		body.addChild(tentacle8);
		setRotationAngle(tentacle8, 0.0F, -3.927F, 0.0F);
		tentacle8.texOffs(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		tentacle1.xRot = ageInTicks;
		tentacle2.xRot = ageInTicks;
		tentacle3.xRot = ageInTicks;
		tentacle4.xRot = ageInTicks;
		tentacle5.xRot = ageInTicks;
		tentacle6.xRot = ageInTicks;
		tentacle7.xRot = ageInTicks;
		tentacle8.xRot = ageInTicks;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
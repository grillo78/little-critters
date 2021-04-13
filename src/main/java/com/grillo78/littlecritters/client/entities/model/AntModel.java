package com.grillo78.littlecritters.client.entities.model;// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.grillo78.littlecritters.common.entities.AntEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AntModel<T extends AntEntity> extends EntityModel<T> {
	private final ModelRenderer root;
	private final ModelRenderer thorax;
	private final ModelRenderer lleg3;
	private final ModelRenderer rleg1;
	private final ModelRenderer lleg3_1;
	private final ModelRenderer lleg2;
	private final ModelRenderer lleg1;
	private final ModelRenderer rleg2;
	private final ModelRenderer head;
	private final ModelRenderer abdomen;

	public AntModel() {
		texWidth = 64;
		texHeight = 64;

		root = new ModelRenderer(this);
		root.setPos(0.0F, 24.0F, 0.0F);
		

		thorax = new ModelRenderer(this);
		thorax.setPos(0.0F, -8.0F, -5.0F);
		root.addChild(thorax);
		thorax.texOffs(32, 0).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 9.0F, 0.0F, false);

		lleg3 = new ModelRenderer(this);
		lleg3.setPos(3.0F, 0.0F, 5.5F);
		thorax.addChild(lleg3);
		setRotationAngle(lleg3, 0.0F, -0.7854F, 0.7854F);
		lleg3.texOffs(32, 14).addBox(0.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		rleg1 = new ModelRenderer(this);
		rleg1.setPos(-3.0F, 0.0F, 3.5F);
		thorax.addChild(rleg1);
		setRotationAngle(rleg1, 0.0F, -0.7854F, -0.7854F);
		rleg1.texOffs(32, 14).addBox(-14.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		lleg3_1 = new ModelRenderer(this);
		lleg3_1.setPos(-3.0F, 0.0F, 5.5F);
		thorax.addChild(lleg3_1);
		setRotationAngle(lleg3_1, 0.0F, 0.7854F, -0.7854F);
		lleg3_1.texOffs(32, 14).addBox(-14.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		lleg2 = new ModelRenderer(this);
		lleg2.setPos(3.0F, 0.0F, 4.5F);
		thorax.addChild(lleg2);
		setRotationAngle(lleg2, 0.0F, 0.0F, 0.576F);
		lleg2.texOffs(32, 14).addBox(0.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		lleg1 = new ModelRenderer(this);
		lleg1.setPos(3.0F, 0.0F, 3.5F);
		thorax.addChild(lleg1);
		setRotationAngle(lleg1, 0.0F, 0.7854F, 0.7854F);
		lleg1.texOffs(32, 14).addBox(0.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		rleg2 = new ModelRenderer(this);
		rleg2.setPos(-3.0F, 0.0F, 4.5F);
		thorax.addChild(rleg2);
		setRotationAngle(rleg2, 0.0F, 0.0F, -0.576F);
		rleg2.texOffs(32, 14).addBox(-14.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		thorax.addChild(head);
		head.texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(1, 21).addBox(2.0F, -6.0F, -13.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);
		head.texOffs(1, 21).addBox(-2.0F, -6.0F, -13.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);

		abdomen = new ModelRenderer(this);
		abdomen.setPos(0.0F, 0.0F, 9.0F);
		thorax.addChild(abdomen);
		abdomen.texOffs(0, 16).addBox(-5.0F, -4.5F, 0.0F, 10.0F, 9.0F, 13.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(AntEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		head.xRot = headPitch * ((float)Math.PI / 180F);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		root.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
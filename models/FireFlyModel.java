// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class FireFlyModel extends EntityModel<Entity> {
	private final ModelRenderer Body;
	private final ModelRenderer Leg3;
	private final ModelRenderer Bum;
	private final ModelRenderer LWing;
	private final ModelRenderer Antenne;
	private final ModelRenderer RWing;
	private final ModelRenderer LWingcase;
	private final ModelRenderer RWingcase;
	private final ModelRenderer Leg2;
	private final ModelRenderer Leg1;

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

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
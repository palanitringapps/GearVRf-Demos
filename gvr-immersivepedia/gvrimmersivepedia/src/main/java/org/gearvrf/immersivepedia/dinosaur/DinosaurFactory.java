/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gearvrf.immersivepedia.dinosaur;

import java.io.IOException;
import java.util.EnumSet;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRAndroidResource.TextureCallback;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRImportSettings;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRTexture;
import org.gearvrf.immersivepedia.R;
import org.gearvrf.immersivepedia.focus.FocusableSceneObject;

public class DinosaurFactory {

    private static final int NEGATIVE_DEGRES_90 = 90;
    private static float adujstRelative = 1f; // default = 1 (close as possible)
    private static float adjustAbsolute = 3f; // default = 0 (close as possible)
    private static float adjustAngleArroundCamera = 0f;

    public static float STYRACOSAURUS_DISTANCE = (4.5506f * adujstRelative) + adjustAbsolute;
    public static float ANKYLOSAURUS_DISTANCE = (4.41f * adujstRelative) + adjustAbsolute;
    public static float APATOSAURUS_DISTANCE = (8.1883f * adujstRelative) + adjustAbsolute;
    public static float TREX_DISTANCE = (6.4987f * adujstRelative) + adjustAbsolute;

    public static float STYRACOSAURUS_ANGLE_AROUND_CAMERA = (4 * NEGATIVE_DEGRES_90) + adjustAngleArroundCamera;
    public static float ANKYLOSAURUS_ANGLE_AROUND_CAMERA = (3 * NEGATIVE_DEGRES_90) + adjustAngleArroundCamera;
    public static float APATOSAURUS_ANGLE_AROUND_CAMERA = (2 * NEGATIVE_DEGRES_90) + adjustAngleArroundCamera;
    public static float TREX_ANGLE_AROUND_CAMERA = (1 * NEGATIVE_DEGRES_90) + adjustAngleArroundCamera;

    private static DinosaurFactory instance;
    private GVRContext gvrContext;

    EnumSet<GVRImportSettings> additionalSettings = EnumSet
            .of(GVRImportSettings.CALCULATE_SMOOTH_NORMALS);
    EnumSet<GVRImportSettings> settings = GVRImportSettings
            .getRecommendedSettingsWith(additionalSettings);

    boolean getFromFile = false;
    private Dinosaur styracosaurus;
    private Dinosaur ankylosaurus;
    private Dinosaur apatosaurus;
    private Dinosaur tRex;

    private DinosaurFactory(GVRContext gvrContext) throws IOException {
        this.gvrContext = gvrContext;
        styracosaurus = createStyrocosaurus();
        ankylosaurus = createAnkylosaurus();
        apatosaurus = createApatosaurus();
        tRex = createTRex();

    }

    public static synchronized DinosaurFactory getInstance(GVRContext gvrContext)
            throws IOException {

        if (instance == null) {
            instance = new DinosaurFactory(gvrContext);

        }
        return instance;
    }

    private Dinosaur createDinosauros(String name, int dinoMeshId, int dinoTextureId,
            int baseMeshId, int groundMeshId) {

        FocusableSceneObject dino = null;
        dino = createDinosaur(dinoMeshId, dinoTextureId);

        FocusableSceneObject base = createDinosaurBase(baseMeshId);
        FocusableSceneObject ground = createDinosaurGround(groundMeshId);

        return new Dinosaur(gvrContext, dino, base, ground);
    }

    private FocusableSceneObject createDinosaur(int dinoMeshId, int dinoTextureId) {
        GVRMesh baseMesh = gvrContext.loadMesh(new GVRAndroidResource(gvrContext, dinoMeshId), settings);
        GVRTexture baseTexture = gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.drawable.empty));
        final FocusableSceneObject dino = new FocusableSceneObject(gvrContext, baseMesh, baseTexture);
        gvrContext.loadTexture(new TextureCallback() {

            @Override
            public void loaded(GVRTexture arg0, GVRAndroidResource arg1) {
                dino.getRenderData().getMaterial().setMainTexture(arg0);
            }

            @Override
            public void failed(Throwable arg0, GVRAndroidResource arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean stillWanted(GVRAndroidResource arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        }, new GVRAndroidResource(gvrContext, dinoTextureId));

        return dino;
    }

    private FocusableSceneObject createDinosaurBase(int baseMeshId) {
        GVRMesh baseMesh = gvrContext.loadMesh(new GVRAndroidResource(gvrContext, baseMeshId), settings);
        GVRTexture baseTexture = gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.raw.base_tex_diffuse));
        FocusableSceneObject dinosaurBase = new FocusableSceneObject(gvrContext, baseMesh, baseTexture);
        return dinosaurBase;
    }

    private FocusableSceneObject createDinosaurGround(int groundMesh) {

        GVRMesh mesh = gvrContext.loadMesh(new GVRAndroidResource(gvrContext, groundMesh), settings);
        GVRTexture groundTexture = gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.drawable.empty));
        final FocusableSceneObject dinosaurGround = new FocusableSceneObject(gvrContext, mesh, groundTexture);
        gvrContext.loadTexture(new TextureCallback() {

            @Override
            public void loaded(GVRTexture texture, GVRAndroidResource arg1) {
                dinosaurGround.getRenderData().getMaterial().setMainTexture(texture);

            }

            @Override
            public void failed(Throwable arg0, GVRAndroidResource arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean stillWanted(GVRAndroidResource arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        }, new GVRAndroidResource(gvrContext, R.raw.ground_tex_diffuse));
        return dinosaurGround;
    }

    private Dinosaur createStyrocosaurus() {

        return createDinosauros("styracosaurus",
                R.raw.styracosaurus_mesh, R.raw.styracosaurus_tex_diffuse,
                R.raw.styracosaurus_base_mesh, R.raw.styracosaurus_ground_mesh);

    }

    private Dinosaur createAnkylosaurus() {

        return createDinosauros("ankylosaurus",
                R.raw.ankylosaurus_mesh, R.raw.ankyosaurus_tex_diffuse,
                R.raw.ankylosaurus_base_mesh, R.raw.ankylosaurus_ground_mesh);
    }

    private Dinosaur createApatosaurus() {

        return createDinosauros("apatosaurus",
                R.raw.apatosaurus_mesh, R.raw.apatosaurus_tex_diffuse,
                R.raw.apatosaurus_base_mesh, R.raw.apatosaurus_ground_mesh);
    }

    private Dinosaur createTRex() {

        return createDinosauros("trex",
                R.raw.trex_mesh, R.raw.trex_tex_diffuse,
                R.raw.trex_base_mesh, R.raw.trex_ground_mesh);
    }

    public Dinosaur getStyracosaurus() {
        return styracosaurus;
    }

    public Dinosaur getAnkylosaurus() {
        return ankylosaurus;
    }

    public Dinosaur getApatosaurus() {
        return apatosaurus;
    }

    public Dinosaur getTRex() {
        return tRex;
    }

}

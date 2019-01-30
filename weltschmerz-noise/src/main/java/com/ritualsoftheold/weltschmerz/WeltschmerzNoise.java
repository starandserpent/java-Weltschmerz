package com.ritualsoftheold.weltschmerz;

import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleBasisFunction;
import com.sudoplay.joise.module.ModuleFractal;
public class WeltschmerzNoise {

    private ModuleFractal gen;

    public WeltschmerzNoise(long seed, int octaves, double frequency){
        //Creates basic fractal module
        gen = new ModuleFractal();
        gen.setAllSourceBasisTypes(ModuleBasisFunction.BasisType.SIMPLEX);
        gen.setAllSourceInterpolationTypes(ModuleBasisFunction.InterpolationType.CUBIC);
        gen.setNumOctaves(octaves);
        gen.setFrequency(frequency);
        gen.setType(ModuleFractal.FractalType.RIDGEMULTI);
        gen.setSeed(seed);
    }

    public ModuleAutoCorrect generateNoise(float min, float max){
        /*
         * ... route it through an autocorrection module...
         *
         * This module will sample it's source multiple times and attempt to
         * auto-correct the output to the range specified.
         */

        ModuleAutoCorrect ac = new ModuleAutoCorrect();
        ac.setSource(gen); // set source (can usually be either another Module or a double value; see specific module for details)
        ac.setRange(0.0f, 1.0f); // set the range to auto-correct to
        ac.setSamples(10000); // set how many samples to take
        ac.calculate2D(); // perform the calculations
        return ac;
    }
}

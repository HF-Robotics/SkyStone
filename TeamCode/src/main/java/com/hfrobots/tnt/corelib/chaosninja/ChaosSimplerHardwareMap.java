/*
 Copyright (c) 2019 HF Robotics (http://www.hfrobots.com)

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.hfrobots.tnt.corelib.chaosninja;

import com.hfrobots.tnt.corelib.util.RealSimplerHardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.revextensions2.ExpansionHubMotor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A SimplerHardwareMap that returns chaotic implementations of some hardware
 */
public class ChaosSimplerHardwareMap extends RealSimplerHardwareMap {
    private final Map<String, HardwareDevice> chaoticHardware = new HashMap<>();

    public ChaosSimplerHardwareMap(HardwareMap realMap) {
        super(realMap);
    }

    @Override
    public <T> List<T> getAll(Class<? extends T> classOrInterface) {
        return super.getAll(classOrInterface);
    }

    @Override
    public <T> T get(Class<? extends T> classOrInterface, String deviceName) {
        T originalDevice = super.get(classOrInterface, deviceName);

        if (originalDevice == null) {
            return null;
        }

        // Fixme: Should be able to send in a ChaoticMotor to an ExpansionHubMotor for REVOptimized!
        if (classOrInterface.isAssignableFrom(DcMotorEx.class)) {

            final ChaoticMotor chaoticMotor;

            if (!chaoticHardware.containsKey(deviceName)) {
                chaoticMotor = new ChaoticMotor((DcMotorEx)originalDevice);
                chaoticHardware.put(deviceName, chaoticMotor);
            } else {
                chaoticMotor = (ChaoticMotor) chaoticHardware.get(deviceName);
            }

            return classOrInterface.cast(chaoticMotor);
        }

        return originalDevice;
    }
}
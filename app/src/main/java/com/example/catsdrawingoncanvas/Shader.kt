package com.example.catsdrawingoncanvas

import org.intellij.lang.annotations.Language

@Language("AGSL")
const val SHADER_PROGRAM = """
    const float steps = 150;
    
    float calcLevel(vec2 coords) {
        float x, y, x2, y2;
        for (float i = 0; i < steps; i += 1) {
            y = 2 * x * y + coords.y;
            x = x2 - y2 + coords.x;
            x2 = x * x;
            y2 = y * y;
            if (x2 + y2 > 4) {
                return i / steps;
            }
        }
        return 1.0;
    }
    
    vec4 calcColor(vec2 coords, float level) {
        const vec2 redcenter1 = vec2(2, 1);
        const vec2 bluecenter1 = vec2(1, -1);
        const vec2 redcenter2 = vec2(-2, -1);
        const vec2 bluecenter2 = vec2(-1, 1);
        float red1 = distance(redcenter1, coords) / 3;
        float red2 = distance(redcenter2, coords) / 4;
        float blue1 = distance(bluecenter1, coords) / 2;
        float blue2 = distance(bluecenter2, coords) / 3;
        float red = (1.5 - min(red1, red2)) * pow(level, 3 / 16.0);
        float blue = (1.2 - min(blue1, blue2)) * pow(level, 3 / 16.0);
        return vec4(red, level, blue, level == 0 ? 0 : 1);
    }
    
    vec4 main (in vec2 coord) {
        vec2 flippedCoords = vec2(-coord.y, coord.x);
        float level = calcLevel(flippedCoords);
        return calcColor(flippedCoords, level);
    }
"""

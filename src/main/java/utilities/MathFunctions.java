package utilities;

public class MathFunctions {

    public String checkElementRotation(String transformValue) {
        // Print the value for debugging purposes
        System.out.println("Transform Value: " + transformValue);

        // Check if the transform value corresponds to a rotation
        if (transformValue.startsWith("rotate")) {
            // Extract the rotation angle
            String angleStr = transformValue.substring(7, transformValue.length() - 1); // Remove 'rotate(' and ')'
            double angle = Double.parseDouble(angleStr.replace("deg", ""));
            //System.out.println("Rotation angle: " + angle + " degrees");

            // Check if the rotation angle is zero (i.e., not rotated)
            if (angle == 0) {
                //System.out.println("Icon is not rotated.");
                return "Icon is not rotated";
            } else {
                //System.out.println("Icon is rotated by " + angle + " degrees.");
                return "Icon is rotated by " + angle + " degrees.";

            }
        } else if (transformValue.startsWith("matrix")) {
            // Handle matrix transformation
            String[] values = transformValue.substring(7, transformValue.length() - 1).split(", ");

            double m11 = Double.parseDouble(values[0]);
            double m12 = Double.parseDouble(values[1]);
            double m21 = Double.parseDouble(values[2]);
            double m22 = Double.parseDouble(values[3]);

            // Calculate the rotation angle from matrix values
            double angle = Math.atan2(m21, m11) * (180 / Math.PI);
            angle = angle < 0 ? angle + 360 : angle;

            //System.out.println("Rotation angle from matrix: " + angle + " degrees");

            // Check if the rotation angle is zero
            if (Math.abs(angle) < 0.01) { // Tolerance for floating point comparison
                //System.out.println("Icon is not rotated.");
                return "Icon is not rotated";
            } else {
                //System.out.println("Icon is rotated by " + angle + " degrees.");
                return "Icon is rotated by " + angle + " degrees.";
            }
        } else {
            //System.out.println("Transform value does not contain rotation.");
            return "Transform value does not contain rotation.";
        }
    }
}

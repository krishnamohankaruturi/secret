package edu.ku.cete.util;

public enum ProjectionColorCodes {

	KAP("#2FA7DF"), KELPA2("#e9841c"), CPASS("#a8a7a5"), DLM("#f0cf0e"), SCORING("#CE39FF"), PLTW("#2FA7DF");

	private String colorCode;

	private ProjectionColorCodes(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getColorCode() {
		return colorCode;
	}

	public static ProjectionColorCodes getByAssessmentCode(String assessmentProgramCode) {
		if (assessmentProgramCode != null) {
			for (ProjectionColorCodes rule : ProjectionColorCodes.values()) {
				if (rule.name().equals(assessmentProgramCode)) {
					return rule;
				}
			}
		}
		return null;
	}

}

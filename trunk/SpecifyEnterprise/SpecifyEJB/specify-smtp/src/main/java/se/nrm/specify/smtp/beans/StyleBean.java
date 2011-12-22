package se.nrm.specify.smtp.beans;

import java.io.Serializable;
import se.nrm.specify.smtp.util.CSSName;

/**
 *
 * @author idali
 */
public class StyleBean implements Serializable {

    private String openFormButton1;
    private String openFormButton2;
    private String openFormButton3;
    private String openFormButton4;
    private String openFormButton5;
    private String openFormButton6;
    private String openFormButton7;
    private String openFormButton8;
    private String openFormButton9;
    private String openFormButton10;
    private String openFormButton11;
    private String disabled1;
    private String disabled2;
    private String disabled3;
    private String disabled4;
    private String disabled5;
    private String disabled6;
    private String disabled7;
    private String disabled8;
    private String disabled9;
    private String disabled10;
    private String disabled11;
    private String div1visibility;
    private String div2visibility;
    private String div3visibility;
    private String div4visibility;
    private String div5visibility;
    private String div6visibility;
    private String div7visibility;
    private String div8visibility;
    private String div9visibility;
    private String div10visibility;
    private String div11visibility;
    private String newrecorddiv;
    private String dialogbox;
    private String dialogboxedit;
    private String statisticeventdiv;
    private String statistictrapdiv;
    private String statistictrapcountdiv;
    private String statisticeventcountdiv;
    private String statisticrawsample;
    private String samplelistdiv;
    private String samplelisteditdiv;
    private static final String INACTIVE_BUTTON = CSSName.getInstance().INACTIVE_BUTTON;
    private static final String ACTIVE_BUTTON = CSSName.getInstance().ACTIVE_BUTTON;
    private static final String DISABLED = CSSName.getInstance().DISABLE_TRUE;
    private static final String ENABLED = CSSName.getInstance().DISABLE_FALSE;
    private static final String INVISABLE = CSSName.getInstance().DIV_INVISIBLE;
    private static final String VISIBLE = CSSName.getInstance().DIV_VISIBLE;
    private static final String SAMPLE_NUMBER_ENABLED = CSSName.getInstance().SAMPLE_NUMBER_ENABLED;
    private static final String SAMPLE_NUMBER_DISABLED = CSSName.getInstance().SMAPLE_NUMBER_DISABLED;

    public StyleBean() {
    }

    public void initStyleBean() {

        openFormButton1 = INACTIVE_BUTTON;
        openFormButton2 = INACTIVE_BUTTON;
        openFormButton3 = INACTIVE_BUTTON;
        openFormButton4 = INACTIVE_BUTTON;
        openFormButton5 = INACTIVE_BUTTON;
        openFormButton6 = INACTIVE_BUTTON;
        openFormButton7 = INACTIVE_BUTTON;
        openFormButton8 = INACTIVE_BUTTON;
        openFormButton9 = INACTIVE_BUTTON;
        openFormButton10 = INACTIVE_BUTTON;
        openFormButton11 = INACTIVE_BUTTON;

        disabled1 = DISABLED;
        disabled2 = DISABLED;
        disabled3 = DISABLED;
        disabled4 = DISABLED;
        disabled5 = DISABLED;
        disabled6 = DISABLED;
        disabled7 = DISABLED;
        disabled8 = DISABLED;
        disabled9 = DISABLED;
        disabled10 = DISABLED;
        disabled11 = DISABLED;

        div1visibility = INVISABLE;
        div2visibility = INVISABLE;
        div3visibility = INVISABLE;
        div4visibility = INVISABLE;
        div5visibility = INVISABLE;
        div6visibility = INVISABLE;
        div7visibility = INVISABLE;
        div8visibility = INVISABLE;
        div9visibility = INVISABLE;
        div10visibility = INVISABLE;
        div11visibility = INVISABLE;
    }

    public void resetStyle(boolean[] value) {

        for (int i = 0; i < value.length; i++) {
            if (value[i]) {
                setStyle(i + 1, VISIBLE, ACTIVE_BUTTON, ENABLED);
            } else {
                setStyle(i + 1, INVISABLE, INACTIVE_BUTTON, DISABLED);
            }
        }
    }

    public String getDisabled1() {
        return disabled1;
    }

    public void setDisabled1(String disabled1) {
        this.disabled1 = disabled1;
    }

    public String getDisabled2() {
        return disabled2;
    }

    public void setDisabled2(String disabled2) {
        this.disabled2 = disabled2;
    }

    public String getDisabled3() {
        return disabled3;
    }

    public void setDisabled3(String disabled3) {
        this.disabled3 = disabled3;
    }

    public String getDisabled4() {
        return disabled4;
    }

    public void setDisabled4(String disabled4) {
        this.disabled4 = disabled4;
    }

    public String getDisabled5() {
        return disabled5;
    }

    public void setDisabled5(String disabled5) {
        this.disabled5 = disabled5;
    }

    public String getDisabled6() {
        return disabled6;
    }

    public void setDisabled6(String disabled6) {
        this.disabled6 = disabled6;
    }

    public String getDisabled7() {
        return disabled7;
    }

    public void setDisabled7(String disabled7) {
        this.disabled7 = disabled7;
    }

    public String getDisabled10() {
        return disabled10;
    }

    public void setDisabled10(String disabled10) {
        this.disabled10 = disabled10;
    }

    public String getDisabled11() {
        return disabled11;
    }

    public void setDisabled11(String disabled11) {
        this.disabled11 = disabled11;
    }

    public String getDisabled8() {
        return disabled8;
    }

    public void setDisabled8(String disabled8) {
        this.disabled8 = disabled8;
    }

    public String getDisabled9() {
        return disabled9;
    }

    public void setDisabled9(String disabled9) {
        this.disabled9 = disabled9;
    }

    public String getOpenFormButton1() {
        return openFormButton1;
    }

    public void setOpenFormButton1(String openFormButton1) {
        this.openFormButton1 = openFormButton1;
    }

    public String getOpenFormButton2() {
        return openFormButton2;
    }

    public void setOpenFormButton2(String openFormButton2) {
        this.openFormButton2 = openFormButton2;
    }

    public String getOpenFormButton3() {
        return openFormButton3;
    }

    public void setOpenFormButton3(String openFormButton3) {
        this.openFormButton3 = openFormButton3;
    }

    public String getOpenFormButton4() {
        return openFormButton4;
    }

    public void setOpenFormButton4(String openFormButton4) {
        this.openFormButton4 = openFormButton4;
    }

    public String getOpenFormButton5() {
        return openFormButton5;
    }

    public void setOpenFormButton5(String openFormButton5) {
        this.openFormButton5 = openFormButton5;
    }

    public String getOpenFormButton6() {
        return openFormButton6;
    }

    public void setOpenFormButton6(String openFormButton6) {
        this.openFormButton6 = openFormButton6;
    }

    public String getOpenFormButton7() {
        return openFormButton7;
    }

    public void setOpenFormButton7(String openFormButton7) {
        this.openFormButton7 = openFormButton7;
    }

    public String getOpenFormButton10() {
        return openFormButton10;
    }

    public void setOpenFormButton10(String openFormButton10) {
        this.openFormButton10 = openFormButton10;
    }

    public String getOpenFormButton11() {
        return openFormButton11;
    }

    public void setOpenFormButton11(String openFormButton11) {
        this.openFormButton11 = openFormButton11;
    }

    public String getOpenFormButton8() {
        return openFormButton8;
    }

    public void setOpenFormButton8(String openFormButton8) {
        this.openFormButton8 = openFormButton8;
    }

    public String getOpenFormButton9() {
        return openFormButton9;
    }

    public void setOpenFormButton9(String openFormButton9) {
        this.openFormButton9 = openFormButton9;
    }

    public String getDiv1visibility() {
        return div1visibility;
    }

    public void setDiv1visibility(String div1visibility) {
        this.div1visibility = div1visibility;
    }

    public String getDiv2visibility() {
        return div2visibility;
    }

    public void setDiv2visibility(String div2visibility) {
        this.div2visibility = div2visibility;
    }

    public String getDiv3visibility() {
        return div3visibility;
    }

    public void setDiv3visibility(String div3visibility) {
        this.div3visibility = div3visibility;
    }

    public String getDiv4visibility() {
        return div4visibility;
    }

    public void setDiv4visibility(String div4visibility) {
        this.div4visibility = div4visibility;
    }

    public String getDiv5visibility() {
        return div5visibility;
    }

    public void setDiv5visibility(String div5visibility) {
        this.div5visibility = div5visibility;
    }

    public String getDiv6visibility() {
        return div6visibility;
    }

    public void setDiv6visibility(String div6visibility) {
        this.div6visibility = div6visibility;
    }

    public String getDiv7visibility() {
        return div7visibility;
    }

    public void setDiv7visibility(String div7visibility) {
        this.div7visibility = div7visibility;
    }

    public String getDiv8visibility() {
        return div8visibility;
    }

    public void setDiv8visibility(String div8visibility) {
        this.div8visibility = div8visibility;
    }

    public String getDiv9visibility() {
        return div9visibility;
    }

    public void setDiv9visibility(String div9visibility) {
        this.div9visibility = div9visibility;
    }

    public String getDiv10visibility() {
        return div10visibility;
    }

    public void setDiv10visibility(String div10visibility) {
        this.div10visibility = div10visibility;
    }

    public String getDiv11visibility() {
        return div11visibility;
    }

    public void setDiv11visibility(String div11visibility) {
        this.div11visibility = div11visibility;
    }

    public String getNewrecorddiv() {
        return newrecorddiv;
    }

    public void setNewrecorddiv(String newrecorddiv) {
        this.newrecorddiv = newrecorddiv;
    }

    public String getDisabled() {
        return DISABLED;
    }

    public String getEnabled() {
        return ENABLED;
    }

    public String getSampleNumberEnabled() {
        return SAMPLE_NUMBER_ENABLED;
    }

    public String getSampleNumberDisabled() {
        return SAMPLE_NUMBER_DISABLED;
    }

    public String getDialogbox() {
        return dialogbox;
    }

    public void setDialogbox(String dialogbox) {
        this.dialogbox = dialogbox;
    }

    public String getDialogboxedit() {
        return dialogboxedit;
    }

    public void setDialogboxedit(String dialogboxedit) {
        this.dialogboxedit = dialogboxedit;
    }

    public String getStatisticeventdiv() {
        return statisticeventdiv;
    }

    public void setStatisticeventdiv(boolean isVisible) {
        if (isVisible) {
            this.statisticeventdiv = VISIBLE;
        } else {
            this.statisticeventdiv = INVISABLE;
        }
    }

    public String getStatisticeventcountdiv() {
        return statisticeventcountdiv;
    }

    public void setStatisticeventcountdiv(boolean isVisible) {
        if (isVisible) {
            this.statisticeventcountdiv = VISIBLE;
        } else {
            this.statisticeventcountdiv = INVISABLE;
        }
    }

    public String getStatistictrapcountdiv() {
        return statistictrapcountdiv;
    }

    public void setStatistictrapcountdiv(boolean isVisible) {
        if (isVisible) {
            this.statistictrapcountdiv = VISIBLE;
        } else {
            this.statistictrapcountdiv = INVISABLE;
        }
    }

    public String getStatistictrapdiv() {
        return statistictrapdiv;
    }

    public void setStatistictrapdiv(boolean isVisible) {
        if (isVisible) {
            this.statistictrapdiv = VISIBLE;
        } else {
            this.statistictrapdiv = INVISABLE;
        }
    }

    public String getStatisticrawsample() {
        return statisticrawsample;
    }

    public void setStatisticrawsample(boolean isVisible) {
        if (isVisible) {
            this.statisticrawsample = VISIBLE;
        } else {
            this.statisticrawsample = INVISABLE;
        }
    }

    public String getSamplelistdiv() {
        return samplelistdiv;
    }

    public void setSamplelistdiv(boolean isVisible) {
        if (isVisible) {
            this.samplelistdiv = VISIBLE;
        } else {
            this.samplelistdiv = INVISABLE;
        }
    }

    public String getSamplelisteditdiv() {
        return samplelisteditdiv;
    }

    public void setSamplelisteditdiv(boolean isVisible) {
        this.samplelisteditdiv = (isVisible) ? VISIBLE : INVISABLE;
    }

    private void setStyle(int i, String visibility, String activity, String disablity) {
        switch (i) {
            case 1:
                div1visibility = visibility;
                openFormButton1 = activity;
                disabled1 = disablity;
                break;
            case 2:
                div2visibility = visibility;
                openFormButton2 = activity;
                disabled2 = disablity;
                break;
            case 3:
                div3visibility = visibility;
                openFormButton3 = activity;
                disabled3 = disablity;
                break;
            case 4:
                div4visibility = visibility;
                openFormButton4 = activity;
                disabled4 = disablity;
                break;
            case 5:
                div5visibility = visibility;
                openFormButton5 = activity;
                disabled5 = disablity;
                break;
            case 6:
                div6visibility = visibility;
                openFormButton6 = activity;
                disabled6 = disablity;
                break;
            case 7:
                div7visibility = visibility;
                openFormButton7 = activity;
                disabled7 = disablity;
                break;
            case 8:
                div8visibility = visibility;
                openFormButton8 = activity;
                disabled8 = disablity;
                break;
            case 9:
                div9visibility = visibility;
                openFormButton9 = activity;
                disabled9 = disablity;
                break;
            case 10:
                div10visibility = visibility;
                openFormButton10 = activity;
                disabled10 = disablity;
                break;
            case 11:
                div11visibility = visibility;
                openFormButton11 = activity;
                disabled11 = disablity;
                break;
        }
    }
}

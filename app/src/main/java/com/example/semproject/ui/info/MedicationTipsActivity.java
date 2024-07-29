package com.example.semproject.ui.info;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.semproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicationTipsActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private TipsAdapter tipsAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_tips);

        expandableListView = findViewById(R.id.expandableListView);
        prepareListData();
        tipsAdapter = new TipsAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(tipsAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding header data
        listDataHeader.add("Follow medication schedules and doses");
        listDataHeader.add("Double check information with your pharmacist");
        listDataHeader.add("Write down the medication schedule and track changes");
        listDataHeader.add("Consider a pill organizer");
        listDataHeader.add("Make sure all clinicians know what medications you take");
        listDataHeader.add("Manage medications safely");
        listDataHeader.add("Managing Expired or Discontinued Medications");
        listDataHeader.add("Proper Disposal of Expired or Discontinued Medication");

        // Adding child data
        List<String> followSchedules = new ArrayList<>();
        followSchedules.add("Understand your medication routine. Confirm the exact dose and timing of each medication with your pharmacist. Follow the schedule exactly, and take the exact dose prescribed. If needed, remember which medications need to be taken on an empty stomach or with food.");

        List<String> doubleCheck = new ArrayList<>();
        doubleCheck.add("Verify your information with your pharmacist each time you fill or start a new prescription to keep you on track.");

        List<String> writeDownSchedule = new ArrayList<>();
        writeDownSchedule.add("Place your daily medication routine on a calendar or chart. Printable medication schedule templates are available online to make it easier for you. Update your schedule each time your medicine changes.");

        List<String> considerPillOrganizer = new ArrayList<>();
        considerPillOrganizer.add("Use a weekly or daily pill organizer — especially when taking several medicines — to help make sure you get the right dose at the right time. A pillbox with compartments for each day of the week — and for morning, noon and night if you take medications several times a day — lets you know at a glance whether you’ve taken your medicines yet. Ask at your pharmacy what pill organizers are available. Smartphone or computer-based apps may also help.");

        List<String> informClinicians = new ArrayList<>();
        informClinicians.add("If you go to different clinicians for different conditions, it’s extremely important to tell each of them about all of the medications you are taking. It may help to carry a list with you at all times. Use a medicine wallet card. Ask your pharmacist if a medicine wallet card is available, or make your own. The card will help you keep an up-to-date list of your medicines with you. Make sure your pharmacy has a record of all the medicines you take. Use your medication list to let your pharmacist know about all of your prescriptions and over-the-counter medicines.");

        List<String> manageSafely = new ArrayList<>();
        manageSafely.add("Where you store medications and how you take them can have a big impact on their effectiveness and your safety. Ask your physicians and clinicians about possible side effects or medication interactions that you should be aware of. Confirm the best place to store your medicines in your home. Most importantly, know who to call if you have a bad reaction or takes a prescription differently than prescribed. Store medicines in a proper location. Medication should always be stored safely in a dry, cool place. For this reason, avoid keeping prescriptions in the bathroom. Keep medicine in a childproof place. If children are around, keep medicine containers out of reach, especially those without childproof caps.");

        List<String> manageExpired = new ArrayList<>();
        manageExpired.add("If your physician tells you to discontinue a medicine, dispose of it immediately. Also dispose of medications that are expired. Do not keep them for future needs. You could have side effects or a medicine interaction if you accidentally take that medication.");

        List<String> disposeProperly = new ArrayList<>();
        disposeProperly.add("Some medicines are harmful and could be fatal if accidentally taken by children or anyone else. It is important to always dispose of medications properly. Inquire at your pharmacy about medicine take back programs in your area. If one is not available, follow disposal instructions on the medicine label or the patient information that accompanies the medicine. If no instructions are given, crush and mix medicines with coffee grounds, cat litter or food scraps, then seal them in a bag or a container (such as a jar or a margarine tub) and discard them in the regular trash.");

        listDataChild.put(listDataHeader.get(0), followSchedules); // Header, Child data
        listDataChild.put(listDataHeader.get(1), doubleCheck);
        listDataChild.put(listDataHeader.get(2), writeDownSchedule);
        listDataChild.put(listDataHeader.get(3), considerPillOrganizer);
        listDataChild.put(listDataHeader.get(4), informClinicians);
        listDataChild.put(listDataHeader.get(5), manageSafely);
        listDataChild.put(listDataHeader.get(6), manageExpired);
        listDataChild.put(listDataHeader.get(7), disposeProperly);
    }
}

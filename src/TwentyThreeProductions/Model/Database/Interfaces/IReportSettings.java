package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.ReportSettings;

public interface IReportSettings {
    ReportSettings getStockSettings();
    ReportSettings getMOTSettings();
    void updateDate(ReportSettings reportSettings);
}

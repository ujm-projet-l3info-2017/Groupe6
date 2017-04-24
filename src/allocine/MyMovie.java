package allocine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.moviejukebox.allocine.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonRootName("movie")
@JsonIgnoreProperties(value = {"trailerEmbed"})
public class MyMovie extends AbstractBaseMapping {

    private static final long serialVersionUID = 100L;

    @JsonProperty("productionYear")
    private int productionYear;
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("trailer")
    private Trailer trailer;
    @JsonProperty("movieCertificate")
    private MovieCertificate movieCertificate;
    @JsonProperty("festivalAward")
    private List<FestivalAward> festivalAwards = new ArrayList<>();
    @JsonProperty("movieType")
    private CodeName movieType;
    @JsonProperty("color")
    private String color;
    @JsonProperty("language")
    private List<CodeName> languages = new ArrayList<>();
    @JsonProperty("budget")
    private String budget;
    @JsonProperty("hasVOD")
    private boolean vod;
    @JsonProperty("hasBluRay")
    private boolean bluray;
    @JsonProperty("hasShowtime")
    private boolean showtime;
    @JsonProperty("dvdReleaseDate")
    private String dvdReleaseDate;
    @JsonProperty("bluRayReleaseDate")
    private String bluRayReleaseDate;
    @JsonProperty("keywords")
    private String keywords;
    @JsonProperty("boxOffice")
    private List<BoxOffice> boxOffice;
    @JsonProperty("formatList")
    private FormatList formatList;
    @JsonProperty("genres")
    private Set<String> genres;

    public Set<String> getGenres()
    {
    	return genres;
    }
    
    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public MovieCertificate getMovieCertificate() {
        return movieCertificate;
    }

    public void setMovieCertificate(MovieCertificate movieCertificate) {
        this.movieCertificate = movieCertificate;
    }

    public List<FestivalAward> getFestivalAwards() {
        return festivalAwards;
    }

    public void setFestivalAwards(List<FestivalAward> festivalAwards) {
        this.festivalAwards = festivalAwards;
    }

    public CodeName getMovieType() {
        return movieType;
    }

    public void setMovieType(CodeName movieType) {
        this.movieType = movieType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(CodeName color) {
        this.color = color.getName();
    }

    public List<CodeName> getLanguages() {
        return languages;
    }

    public void setLanguages(List<CodeName> languages) {
        this.languages = languages;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public boolean isVod() {
        return vod;
    }

    public void setVod(boolean vod) {
        this.vod = vod;
    }

    public boolean isBluray() {
        return bluray;
    }

    public void setBluray(boolean bluray) {
        this.bluray = bluray;
    }

    public boolean isShowtime() {
        return showtime;
    }

    public void setShowtime(boolean showtime) {
        this.showtime = showtime;
    }

    public String getDvdReleaseDate() {
        return dvdReleaseDate;
    }

    public void setDvdReleaseDate(String dvdReleaseDate) {
        this.dvdReleaseDate = dvdReleaseDate;
    }

    public String getBluRayReleaseDate() {
        return bluRayReleaseDate;
    }

    public void setBluRayReleaseDate(String bluRayReleaseDate) {
        this.bluRayReleaseDate = bluRayReleaseDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<BoxOffice> getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(List<BoxOffice> boxOffice) {
        this.boxOffice = boxOffice;
    }

    public FormatList getFormatList() {
        return formatList;
    }

    public void setFormatList(FormatList formatList) {
        this.formatList = formatList;
    }

}
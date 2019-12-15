package com.app.spotifydemo.spotify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class GetArtistsTopTracksExample {
    private static final String id = "6M2wZ9GZgrQXHCFfjv46we";
    private static final CountryCode countryCode = CountryCode.TR;

    private static SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("3279a43cec8341bda42b97d350fe8997")
            .setClientSecret("b147f12d4aad44eebff5f1f841f35a58")
            .setRedirectUri(SpotifyHttpManager.makeUri("https://example.com/callback/"))
            .build();

    private static GetArtistsTopTracksRequest getArtistsTopTracksRequest = spotifyApi
            .getArtistsTopTracks(id, countryCode)
            .build();

    private static ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();


    public Track[] getArtistsTopTracks_Sync() {
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            Track[] artistsTopTracks = spotifyApi.getArtistsTopTracks(id, countryCode).build().execute();
            return spotifyApi.getArtistsTopTracks(id, countryCode).build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void getArtistsTopTracks_Async() {
        try {
            final CompletableFuture<Track[]> artistsFuture = getArtistsTopTracksRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Track[] tracks = artistsFuture.join();

            System.out.println("Length: " + tracks.length);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
}

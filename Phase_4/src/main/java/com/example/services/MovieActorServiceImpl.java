package com.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.dtos.ActorResponseDto;
import com.example.exceptions.EntityNotFoundException;

@Service
public class MovieActorServiceImpl implements MovieActorService {

    private final MovieService movieService;
    private final ActorService actorService;

    public MovieActorServiceImpl(MovieService movieService, ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @Override
    public List<ActorResponseDto> getActorsInMovie(Integer movieId) {
        movieService.getById(movieId);

        BlobContainerClient blobContainerClient = getBlobContainerClient();

        List<String> files = new ArrayList<>();
        blobContainerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfActorsFromBlobList(files, movieId);
    }


    @Override
    public List<ActorResponseDto> addActorToMovie(String actorId, int movieId) {
        movieService.getById(movieId);
        actorService.getById(actorId);

        String fileName = movieId + "_" + actorId;

        BlobContainerClient blobContainerClient = getBlobContainerClient();
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        if (!blobClient.exists()) {
            blobClient.upload(BinaryData.fromBytes(new byte[] { }));
        }

        List<String> files = new ArrayList<>();
        blobContainerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfActorsFromBlobList(files, movieId);
    }

    @Override
    public List<ActorResponseDto> removeActorFromMovie(String actorId, int movieId) {
        movieService.getById(movieId);
        actorService.getById(actorId);

        String fileName = movieId + "_" + actorId;

        BlobContainerClient blobContainerClient = getBlobContainerClient();
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        if (blobClient.exists()) {
            blobClient.delete();
        }

        List<String> files = new ArrayList<>();
        blobContainerClient.listBlobs().iterator().forEachRemaining(blobItem -> files.add(blobItem.getName()));

        return getListOfActorsFromBlobList(files, movieId);
    }

    private BlobContainerClient getBlobContainerClient() {
        String connectionKey = "DefaultEndpointsProtocol=https;AccountName=moviesactors;AccountKey=9wUB1DVAvIJFDWNS+mYJmBqWvVHSLiXC6/tLuefBPbQTJiaQoVZEYXVu6yjRmneUmgaa0zzx8YZN+AStfYW7vA==;EndpointSuffix=core.windows.net";
        String container = "moviesactors";
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(
                connectionKey).buildClient();
        return blobServiceClient.getBlobContainerClient(container);
    }

    private List<ActorResponseDto> getListOfActorsFromBlobList(List<String> files, Integer movieId) {
        return files.stream()
                .filter(e -> e.startsWith(movieId + "_"))
                .map(e -> e.replace(movieId + "_", ""))
                .map(id -> {
                    try {
                        return actorService.getById(id);
                    } catch (EntityNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

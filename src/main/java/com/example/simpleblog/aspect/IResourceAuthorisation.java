package com.example.simpleblog.aspect;

import java.util.UUID;

public interface IResourceAuthorisation {
    boolean isOwner(UUID ownerId);
}

package ch03.case01;

public interface LoadBalancer {
    void updateCandidate(final Candidate candidate);

    Endpoint nextEndpoint();
}

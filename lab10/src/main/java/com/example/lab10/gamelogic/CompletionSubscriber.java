package com.example.lab10.gamelogic;

import com.example.lab10.model.GameHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class CompletionSubscriber extends SubmissionPublisher<Boolean> implements Flow.Subscriber<Boolean> {
    private List<Flow.Subscription> subscriptions = new ArrayList<>();

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscriptions.add(subscription);
        subscription.request(1);
    }

    @Override
    public void onNext(Boolean completed) {
        if (completed) {
            GameHolder.getInstance().sessionFinished();
        }
        // Request another item from all subscriptions
        for (Flow.Subscription subscription : subscriptions) {
            subscription.request(1);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("All Game Sessions processed");
    }
}
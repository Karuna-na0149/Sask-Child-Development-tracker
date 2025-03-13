package com.sask.tracker.service;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudWatchService {
	@Autowired
	private AmazonCloudWatch cloudWatch;

	public void logMetric(String namespace, String metricName, Double value) {
		Dimension dimension = new Dimension().withName("Project").withValue("ChildDevTracker");
		MetricDatum datum = new MetricDatum().withMetricName(metricName).withUnit(StandardUnit.Count).withValue(value)
				.withDimensions(dimension);

		PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(namespace).withMetricData(datum);

		cloudWatch.putMetricData(request);
	}
}

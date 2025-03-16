// Ensure chartData is defined properly
document.addEventListener("DOMContentLoaded", function () {
    var rawData = document.getElementById('chartData').textContent;
    var chartData = JSON.parse(rawData);

    var categories = chartData.map(data => data.category);
    var completedMilestones = chartData.map(data => data.completed);

    // Define a color palette for better visualization
    var backgroundColors = [
        'rgba(255, 99, 132, 0.6)',   // Red
        'rgba(54, 162, 235, 0.6)',   // Blue
        'rgba(255, 206, 86, 0.6)',   // Yellow
        'rgba(75, 192, 192, 0.6)',   // Green
        'rgba(153, 102, 255, 0.6)',  // Purple
        'rgba(255, 159, 64, 0.6)'    // Orange
    ];

    var borderColors = [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
    ];

    var ctx = document.getElementById('milestoneChart').getContext('2d');
    var milestoneChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: categories,
            datasets: [{
                label: 'Milestones Completed',
                data: completedMilestones,
                backgroundColor: backgroundColors,
                borderColor: borderColors,
                borderWidth: 1,
                barPercentage: 0.6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        color: '#333',
                        font: {
                            size: 14
                        }
                    }
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: 'rgba(0, 0, 0, 0.8)',
                    titleFont: {
                        size: 14
                    },
                    bodyFont: {
                        size: 12
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: '#333',
                        font: {
                            size: 14
                        }
                    },
                    grid: {
                        color: 'rgba(200, 200, 200, 0.3)'
                    }
                },
                x: {
                    ticks: {
                        color: '#333',
                        font: {
                            size: 14
                        }
                    },
                    grid: {
                        color: 'rgba(200, 200, 200, 0.3)'
                    }
                }
            }
        }
    });
});
